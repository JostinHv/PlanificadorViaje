package com.jostin.planificadorviaje.ui.viaje.resumen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentResumenBinding

class ResumenFragment : Fragment() {

    private var _binding: FragmentResumenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResumenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupExpandableCards()
        setupMap()
    }

    private fun setupExpandableCards() {
        setupExpandableCard(
            binding.transportHeader,
            binding.transportContent,
            binding.transportExpandIcon
        )
        setupExpandableCard(
            binding.accommodationHeader,
            binding.accommodationContent,
            binding.accommodationExpandIcon
        )
    }

    private fun setupExpandableCard(header: View, content: View, expandIcon: ImageView) {
        header.setOnClickListener {
            // Toggle la visibilidad del contenido
            val isExpanded = content.visibility == View.VISIBLE
            content.visibility = if (isExpanded) View.GONE else View.VISIBLE

            // Actualizar el ícono según el estado
            val newIcon = if (isExpanded) {
                R.drawable.ic_expand_more
            } else {
                R.drawable.ic_expand_less
            }
            expandIcon.setImageResource(newIcon)
        }
    }


    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            val lima = LatLng(-12.0464, -77.0428)
            googleMap.addMarker(MarkerOptions().position(lima).title("Lima"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 12f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
