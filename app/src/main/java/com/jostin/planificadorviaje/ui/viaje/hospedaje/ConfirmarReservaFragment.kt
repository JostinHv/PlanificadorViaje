package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jostin.planificadorviaje.data.model.Reserva
import com.jostin.planificadorviaje.databinding.FragmentConfirmarReservaBinding
import kotlinx.coroutines.launch

class ConfirmarReservaFragment : Fragment() {
    private var _binding: FragmentConfirmarReservaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HospedajeViewModel by viewModels()
    private val args: ConfirmarReservaFragmentArgs by navArgs() // Recibir los argumentos

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmarReservaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeReserva()
        setupConfirmarButton()

        // Cargar los datos de la reserva seleccionada utilizando el argumento hotelId
        viewModel.loadReserva(args.hotelId)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeReserva() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reservaSeleccionada.collect { reserva ->
                reserva?.let { updateReservaUI(it) }
            }
        }
    }

    private fun updateReservaUI(reserva: Reserva) {
        binding.apply {
            hotelNameText.text = reserva.hotel.nombre
            fechaEntradaText.text = "Fecha de Entrada: ${reserva.fechaEntrada}"
            fechaSalidaText.text = "Fecha de Salida: ${reserva.fechaSalida}"
            personasText.text = "Personas: ${reserva.personas}"
            tipoHabitacionText.text = "Tipo de Habitación: ${reserva.tipoHabitacion}"
            precioTotalText.text = "Precio Total: S/${reserva.precioTotal}"
        }
    }

    private fun setupConfirmarButton() {
        binding.confirmarButton.setOnClickListener {
            // Aquí puedes procesar la reserva y navegar al siguiente fragmento
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
