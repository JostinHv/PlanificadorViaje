package com.jostin.planificadorviaje.ui.viaje.resumen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.Reserva
import com.jostin.planificadorviaje.databinding.FragmentResumenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResumenFragment : Fragment() {

    private var _binding: FragmentResumenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResumenViewModel by viewModels()
    private val args: ResumenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Cargar la reserva seleccionada usando los argumentos pasados
        viewModel.cargarReserva(args.reservaId)
        setupExpandableCards()
        setupMap()
        observeViewModel()
    }
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reserva.collect { reserva ->
                reserva?.let { updateUI(it) }
            }
        }
    }

    private fun updateUI(reserva: Reserva) {
        binding.apply {
            // Datos del hotel
            hotelNameText.text = reserva.hotel.name
            checkInText.text = "Check-in: ${reserva.fechaEntrada}"
            checkOutText.text = "Check-out: ${reserva.fechaSalida}"
            guestsText.text = "${reserva.personas} personas"
            roomTypeText.text = "Habitación: ${reserva.tipoHabitacion}"
            priceText.text = "Precio Total: S/${reserva.precioTotal}"

            // Cambiar imagen del hotel
            val imageUrl = reserva.hotel.image_url
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(imageUrl)
                    .into(binding.headerImage)
            }
        }
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
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            val hotelLocation = viewModel.obtenerLatLng()
            if (hotelLocation != null) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(hotelLocation)
                        .title(viewModel.reserva.value?.hotel?.name)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotelLocation, 14f))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
