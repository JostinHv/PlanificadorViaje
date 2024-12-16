package com.jostin.planificadorviaje.ui.viaje.resumen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentResumenBinding

class ResumenFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentResumenBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResumenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupMap()
        setupTransportSection()
        loadItineraryData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupTransportSection() {
        binding.transportButton.setOnClickListener {
            binding.transportRecyclerView.visibility = if (binding.transportRecyclerView.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun loadItineraryData() {
        // Here you would load the actual data from your ViewModel
        binding.descriptionText.text = getString(R.string.lima_description)
        // Set other data...
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Example coordinates for Lima
        val lima = LatLng(-12.0464, -77.0428)
        map.addMarker(MarkerOptions().position(lima).title("Lima"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 12f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}