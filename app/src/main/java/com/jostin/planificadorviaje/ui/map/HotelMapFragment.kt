package com.jostin.planificadorviaje.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.RequestQueueSingleton
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.utils.ImagenUtils
import org.json.JSONObject
import java.util.*

class HotelMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private var currentMarker: Marker? = null
    private val args: HotelMapFragmentArgs by navArgs()

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private val DEFAULT_ZOOM = 15f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        view.findViewById<View>(R.id.btnShowNearbyHotels).setOnClickListener {
            showNearbyHotels()
        }
    }

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

        // Centrar el mapa en las coordenadas de la ciudad
        val cityLocation = LatLng(args.city.latitude, args.city.longitude)
        val bounds = LatLngBounds.builder()
            .include(
                LatLng(
                    cityLocation.latitude + 0.1,
                    cityLocation.longitude + 0.1
                )
            ) // Extremo superior derecho
            .include(
                LatLng(
                    cityLocation.latitude - 0.1,
                    cityLocation.longitude - 0.1
                )
            ) // Extremo inferior izquierdo
            .build()

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

        // Agregar marcador para la ciudad
        map.addMarker(
            MarkerOptions()
                .position(cityLocation)
                .title("Ciudad: ${args.city.name}")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        map.setOnMapClickListener { latLng ->
            placeMarker(latLng)
        }

        map.setOnMarkerClickListener { marker ->
            if (marker.tag is String) {
                val placeId = marker.tag as String
                fetchPlaceDetails(placeId)
            }
            true
        }
    }

    private fun placeMarker(latLng: LatLng) {
        currentMarker?.remove()
        currentMarker = map.addMarker(
            MarkerOptions().position(latLng).title("Ubicación seleccionada").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        )
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ),
                                    DEFAULT_ZOOM
                                )
                            )
                        }
                    } else {
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                defaultLocation,
                                DEFAULT_ZOOM
                            )
                        )
                        map.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            // Handle exception
        }
    }

    @SuppressLint("MissingPermission")
    private fun moveToCurrentLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                // Usar el ícono personalizado para la ubicación actual
                val icon = BitmapDescriptorFactory.fromBitmap(
                    ImagenUtils.vectorToBitmap(
                        R.drawable.current_location_icon,
                        requireContext()
                    )
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM))
                map.addMarker(
                    MarkerOptions()
                        .position(currentLatLng)
                        .title("Mi ubicación actual")
                        .icon(icon)
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (::map.isInitialized) {
            try {
                if (locationPermissionGranted) {
                    map.isMyLocationEnabled = true
                    map.uiSettings.isMyLocationButtonEnabled = true
                    moveToCurrentLocation()
                } else {
                    map.isMyLocationEnabled = false
                    map.uiSettings.isMyLocationButtonEnabled = false
                    getLocationPermission()
                }
            } catch (e: SecurityException) {
                // Manejar la excepción
            }
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun showNearbyHotels() {
        val location = currentMarker?.position ?: LatLng(args.city.latitude, args.city.longitude)
        fetchNearbyHotels(location)
    }


    private fun fetchNearbyHotels(location: LatLng) {
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=${location.latitude},${location.longitude}" +
                "&radius=1000" +
                "&type=lodging" +
                "&key=AIzaSyDMA96lOlSMlnmikyellEzToDvg9JZdMSY"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val results = jsonObject.getJSONArray("results")

                map.clear()
                currentMarker?.let {
                    map.addMarker(MarkerOptions().position(it.position).title("Selected Location"))
                }

                // Convierte el recurso vectorial en un Bitmap
                val hotelIcon = BitmapDescriptorFactory.fromBitmap(
                    ImagenUtils.vectorToBitmap(R.drawable.ic_hotel_marker, requireContext())
                )

                for (i in 0 until results.length()) {
                    val place = results.getJSONObject(i)
                    val name = place.getString("name")
                    val placeId = place.getString("place_id")
                    val lat =
                        place.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                    val lng =
                        place.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                    val hotelLocation = LatLng(lat, lng)

                    val marker = map.addMarker(
                        MarkerOptions()
                            .position(hotelLocation)
                            .title(name)
                            .icon(hotelIcon)
                    )
                    marker?.tag = placeId
                }
            },
            { error ->
                Toast.makeText(
                    context,
                    "Error fetching hotels: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        RequestQueueSingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun fetchPlaceDetails(placeId: String) {
        val url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "place_id=$placeId" +
                "&fields=name,formatted_address,rating,photos,geometry" +
                "&key=AIzaSyDMA96lOlSMlnmikyellEzToDvg9JZdMSY"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val result = jsonObject.getJSONObject("result")

                val name = result.getString("name")
                val address = result.getString("formatted_address")
                val rating = result.optDouble("rating", 0.0)
                val location = result.getJSONObject("geometry").getJSONObject("location")
                val lat = location.getDouble("lat")
                val lng = location.getDouble("lng")
                // Obtener la referencia de la foto
                var photoUrl: String? = null
                if (result.has("photos")) {
                    val photosArray = result.getJSONArray("photos")
                    if (photosArray.length() > 0) {
                        val photoReference =
                            photosArray.getJSONObject(0).getString("photo_reference")
                        photoUrl =
                            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=AIzaSyDMA96lOlSMlnmikyellEzToDvg9JZdMSY"
                    }
                }
                val amenities = generateRandomAmenities() // Genera amenidades aleatorias
                val price = (50..150).filter { it % 5 == 0 }.random()
                    .toDouble() // Generates a random price divisible by 5
                showConfirmationDialog(name, address, rating, lat, lng, photoUrl, amenities, price)
            },
            { error ->
                Toast.makeText(
                    context,
                    "Error fetching hotel details: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        RequestQueueSingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun showConfirmationDialog(
        name: String,
        address: String,
        rating: Double,
        lat: Double,
        lng: Double,
        photoUrl: String?,
        amenities: List<String>,
        price: Double,
    ) {

        val dialog = Dialog(requireContext(), R.style.FullWidthDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_hotel_confirmation)

        dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val hotelImage = dialog.findViewById<ImageView>(R.id.ivHotelImage)
        val hotelName = dialog.findViewById<TextView>(R.id.tvHotelName)
        val ratingBar = dialog.findViewById<RatingBar>(R.id.rbHotelRating)
        val ratingValue = dialog.findViewById<TextView>(R.id.tvRatingValue)
        val chipGroupAmenities = dialog.findViewById<ChipGroup>(R.id.chipGroupAmenities)
        val hotelAddress = dialog.findViewById<TextView>(R.id.tvHotelAddress)
        val priceView = dialog.findViewById<TextView>(R.id.precioHotel)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)
        val btnConfirm = dialog.findViewById<MaterialButton>(R.id.btnConfirm)

        hotelName.text = name
        hotelAddress.text = address
        ratingBar.rating = rating.toFloat()
        ratingValue.text = String.format("%.1f", rating)
        priceView.text = "S/ $price" // Mostrar precio

        // Agrega las amenidades al ChipGroup
        chipGroupAmenities.removeAllViews()
        for (amenity in amenities) {
            val chip = Chip(requireContext()).apply {
                text = amenity
                isClickable = false
                isCheckable = false
            }
            chipGroupAmenities.addView(chip)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        // Cargar imagen usando Glide
        if (!photoUrl.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(photoUrl)
                .placeholder(R.drawable.hotel_placeholder) // Imagen de marcador de posición
                .into(hotelImage)
        } else {
            hotelImage.setImageResource(R.drawable.hotel_placeholder)
        }
        btnConfirm.setOnClickListener {
            val place = Place(
                id = UUID.randomUUID().toString(),
                name = name,
                address = address,
                latitude = lat,
                longitude = lng,
                imageUrl = photoUrl,
                rating = rating.toFloat(),
                details = amenities,
                price = price // Agregar el precio al objeto Place
            )
            navigateBackWithResult(place)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun navigateBackWithResult(place: Place) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("selected_place", place)
        findNavController().popBackStack()
    }

    private fun generateRandomAmenities(): List<String> {
        val allAmenities = listOf(
            "WiFi Gratis",
            "Piscina",
            "Desayuno Incluido",
            "Gimnasio",
            "Spa",
            "Estacionamiento Gratis"
        )
        return allAmenities.shuffled().take((2..4).random())
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}

