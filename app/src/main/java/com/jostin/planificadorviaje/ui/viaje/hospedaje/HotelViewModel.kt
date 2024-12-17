package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Hotel
import com.jostin.planificadorviaje.data.model.Reserva
import com.jostin.planificadorviaje.data.model.TipoHabitacion
import com.jostin.planificadorviaje.data.repository.HotelRepository
import com.jostin.planificadorviaje.data.repository.ReservaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max
@HiltViewModel
class HotelViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val reservaRepository: ReservaRepository
) : ViewModel() {

    // Estado para lista de hoteles
    private val _hoteles = MutableStateFlow<List<Hotel>>(emptyList())
    val hoteles: StateFlow<List<Hotel>> = _hoteles

    // Estado para la reserva seleccionada
    private val _reservaSeleccionada = MutableStateFlow<Reserva?>(null)
    val reservaSeleccionada: StateFlow<Reserva?> = _reservaSeleccionada

    init {
        cargarHoteles()
    }

    private fun cargarHoteles() {
        viewModelScope.launch {
            try {
                val hotels = hotelRepository.getHotels()
                _hoteles.value = hotels
            } catch (e: Exception) {
                e.printStackTrace() // Manejo básico de errores
            }
        }
    }

    fun loadReserva(hotelId: String) {
        viewModelScope.launch {
            try {
                val reserva = reservaRepository.getReservaByHotelId(hotelId)
                Log.d("HotelViewModel", "Reserva: $reserva")
//                reserva.precioTotal = calcularPrecioTotal(
//                    reserva.hotel,
//                    TipoHabitacion.valueOf(reserva.tipoHabitacion),
//                    personas,
//                    calcularNoches(fechaEntrada, fechaSalida)
//                )
                _reservaSeleccionada.value = reserva
            } catch (e: Exception) {
                Log.e("HotelViewModel", "Error al cargar la reserva", e)  // Añade detalles de la excepción
            }
        }
    }

    private fun calcularNoches(fechaEntrada: String, fechaSalida: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val entrada = dateFormat.parse(fechaEntrada)
        val salida = dateFormat.parse(fechaSalida)
        val diferencia =
            max((salida.time - entrada.time) / (1000 * 60 * 60 * 24), 1) // Al menos 1 noche
        return diferencia.toInt()
    }

    private fun calcularPrecioTotal(
        hotel: Hotel,
        tipoHabitacion: TipoHabitacion,
        personas: Int,
        noches: Int
    ): Double {
        val costoPorPersona = hotel.price_per_person * personas
        val costoPorNoche = hotel.price_per_day * noches
        val costoTipoHabitacion = tipoHabitacion.precioBase
        return costoPorPersona + costoPorNoche + costoTipoHabitacion
    }

}
