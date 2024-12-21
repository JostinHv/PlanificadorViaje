package com.jostin.planificadorviaje.ui.plan.restaurant.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.RequestQueueSingleton
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.utils.ImagenUtils
import org.json.JSONObject
import java.util.*

class RestaurantMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private var currentMarker: Marker? = null

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private val DEFAULT_ZOOM = 15f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        view.findViewById<Button>(R.id.btnShowNearbyRestaurants).setOnClickListener {
            showNearbyRestaurants()
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
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


    private fun placeMarker(latLng: LatLng) {
        currentMarker?.remove()
        currentMarker = map.addMarker(
            MarkerOptions().position(latLng).title("Ubicación seleccionada").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        )
    }

    private fun showNearbyRestaurants() {
        val location = currentMarker?.position ?: (lastKnownLocation?.let {
            LatLng(
                it.latitude,
                it.longitude
            )
        })
        location?.let { fetchNearbyRestaurants(it) }
    }

    private fun fetchNearbyRestaurants(location: LatLng) {
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&radius=1000&type=restaurant&key=AIzaSyDMA96lOlSMlnmikyellEzToDvg9JZdMSY"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val results = jsonObject.getJSONArray("results")

                map.clear()
                // Convertir el ícono del restaurante en un Bitmap
                val restaurantIcon = BitmapDescriptorFactory.fromBitmap(
                    ImagenUtils.vectorToBitmap(
                        R.drawable.ic_restaurant,
                        requireContext()
                    )
                )

                for (i in 0 until results.length()) {
                    val place = results.getJSONObject(i)
                    val name = place.getString("name")
                    val placeId = place.getString("place_id")
                    val lat =
                        place.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                    val lng =
                        place.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                    val restaurantLocation = LatLng(lat, lng)

                    val marker =
                        map.addMarker(
                            MarkerOptions().position(restaurantLocation).title(name)
                                .icon(restaurantIcon)
                        )
                    if (marker != null) {
                        marker.tag = placeId
                    }
                }
            },
            { _ ->
                // Handle error
            })

        // Add the request to the RequestQueue
        RequestQueueSingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun fetchPlaceDetails(placeId: String) {
        val url =
            "https://maps.googleapis.com/maps/api/place/details/json?place_id=$placeId&fields=name,formatted_address,rating,geometry,photos&key=AIzaSyDMA96lOlSMlnmikyellEzToDvg9JZdMSY"

        val stringRequest = StringRequest(Request.Method.GET, url,
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
                showConfirmationDialog(name, address, rating, lat, lng, photoUrl)
            },
            { error ->
                // Handle error
            })

        RequestQueueSingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun showConfirmationDialog(
        name: String,
        address: String,
        rating: Double,
        lat: Double,
        lng: Double,
        photoUrl: String?
    ) {
        val dialog = Dialog(requireContext(), R.style.FullWidthDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_restaurant_confirmation)

        // Make dialog width match parent with margins
        dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // Initialize views
        val restaurantImage = dialog.findViewById<ImageView>(R.id.ivRestaurantImage)
        val restaurantName = dialog.findViewById<TextView>(R.id.tvRestaurantName)
        val ratingBar = dialog.findViewById<RatingBar>(R.id.rbRestaurantRating)
        val ratingValue = dialog.findViewById<TextView>(R.id.tvRatingValue)
        val restaurantAddress = dialog.findViewById<TextView>(R.id.tvRestaurantAddress)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)
        val btnConfirm = dialog.findViewById<MaterialButton>(R.id.btnConfirm)

        // Set values
        restaurantName.text = name
        restaurantAddress.text = address
        ratingBar.rating = rating.toFloat()
        ratingValue.text = String.format("%.1f", rating)

        // Cargar imagen usando Glide
        if (!photoUrl.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(photoUrl)
                .placeholder(R.drawable.restaurant_placeholder) // Imagen de marcador de posición
                .into(restaurantImage)
        } else {
            restaurantImage.setImageResource(R.drawable.restaurant_placeholder)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val place = Place(
                id = UUID.randomUUID().toString(),
                name = name,
                address = address,
                latitude = lat,
                longitude = lng
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


    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}