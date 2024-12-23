package com.jostin.planificadorviaje.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.City
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        observeData()
        viewModel.loadDestinationsWithCoordinates()
    }

    private fun observeData() {
        viewModel.citiesWithCoordinates.observe(viewLifecycleOwner) { cities ->
            showMarkersOnMap(cities)
        }
    }

    private fun showMarkersOnMap(cities: List<City>) {
        if (cities.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()
        cities.forEach { city ->
            val location = LatLng(city.latitude, city.longitude)
            googleMap.addMarker(
                MarkerOptions().position(location).title(city.name)
            )
            boundsBuilder.include(location) // Agrega cada marcador a los límites
        }

        // Ajustar la cámara para mostrar todos los marcadores
        val bounds = boundsBuilder.build()
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(bounds, 100) // Padding de 100px
        )
    }

}
