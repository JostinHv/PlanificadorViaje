package com.jostin.planificadorviaje.ui.viaje.hospedaje

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Hotel
import com.jostin.planificadorviaje.data.model.Reserva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HospedajeViewModel : ViewModel() {
    private val _hoteles = MutableStateFlow<List<Hotel>>(emptyList())
    val hoteles: StateFlow<List<Hotel>> = _hoteles.asStateFlow()

    private val _reservaSeleccionada = MutableStateFlow<Reserva?>(null)
    val reservaSeleccionada: StateFlow<Reserva?> = _reservaSeleccionada.asStateFlow()

    init {
        // Simular carga de hoteles
        cargarHoteles()
    }

    private fun cargarHoteles() {
        viewModelScope.launch {
            _hoteles.value = listOf(
                Hotel("1", "Wyndham Costa del Sol Lima", "Económico", 150.0),
                Hotel("2", "Hotel Miraflores", "Económico", 150.0),
                Hotel("3", "Holiday Inn Lima", "Económico", 150.0)
            )
        }
    }

    fun seleccionarHotel(
        hotel: Hotel,
        fechaEntrada: String,
        fechaSalida: String,
        personas: Int,
        tipoHabitacion: String
    ) {
        val precioTotal = hotel.precioNoche * personas
        _reservaSeleccionada.value = Reserva(
            id = TODO(),
            hotel = hotel,
            fechaEntrada = fechaEntrada,
            fechaSalida = fechaSalida,
            personas = personas,
            tipoHabitacion = tipoHabitacion,
            precioTotal = precioTotal,
        )
    }

    fun loadReserva(hotelId: String) {
        val mockReserva = Reserva(
            hotel = _hoteles.value.first { it.id == hotelId },
            fechaEntrada = "05/10/2024",
            fechaSalida = "07/10/2024",
            personas = 2,
            tipoHabitacion = "Doble",
            precioTotal = 300.0,
            id = hotelId
        )
        _reservaSeleccionada.value = mockReserva
    }
}