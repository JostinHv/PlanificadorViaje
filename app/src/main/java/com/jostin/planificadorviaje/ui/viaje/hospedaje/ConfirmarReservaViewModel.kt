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
class ConfirmarReservaViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val reservaRepository: ReservaRepository
) : ViewModel() {

    // Estado para lista de hoteles
    private val _hoteles = MutableStateFlow<List<Hotel>>(emptyList())
    val hoteles: StateFlow<List<Hotel>> = _hoteles

    // Estado para la reserva seleccionada
    private val _reservaSeleccionada = MutableStateFlow<Reserva?>(null)
    val reservaSeleccionada: StateFlow<Reserva?> = _reservaSeleccionada

    /**
     * Cargar la reserva según el ID del hotel y los detalles de la reserva.
     */
    fun loadReserva(hotelId: String, fechaEntrada: String, fechaSalida: String, personas: Int) {
        viewModelScope.launch {
            try {
                // Obtener la reserva del repositorio
                val reserva = reservaRepository.getReservaByHotelId(hotelId)
                Log.d("ConfirmarReservaViewModel", "Reserva obtenida: $reserva")

                if (reserva != null) {
                    reserva.fechaEntrada = fechaEntrada
                    reserva.fechaSalida = fechaSalida
                    reserva.personas = personas
                    // Calcular el precio total de la reserva
                    val tipoHabitacionEnum = convertirTipoHabitacion(reserva.tipoHabitacion)
                    reserva.precioTotal = calcularPrecioTotal(
                        reserva.hotel,
                        tipoHabitacionEnum,
                        personas,
                        calcularNoches(fechaEntrada, fechaSalida)
                    )
                }

                // Actualizar el estado de la reserva seleccionada
                _reservaSeleccionada.value = reserva
            } catch (e: Exception) {
                Log.e(
                    "ConfirmarReservaViewModel",
                    "Error al cargar la reserva",
                    e
                ) // Manejo de errores detallado
            }
        }
    }

    /**
     * Calcular el número de noches entre la fecha de entrada y la fecha de salida.
     */
    private fun calcularNoches(fechaEntrada: String, fechaSalida: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val entrada = dateFormat.parse(fechaEntrada)
        val salida = dateFormat.parse(fechaSalida)
        val diferencia =
            max((salida.time - entrada.time) / (1000 * 60 * 60 * 24), 1) // Al menos 1 noche
        return diferencia.toInt()
    }

    /**
     * Calcular el precio total de una reserva.
     */
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

    /**
     * Convertir el tipo de habitación almacenado como `String` en la base de datos a un `TipoHabitacion` válido.
     */
    private fun convertirTipoHabitacion(tipoHabitacion: String): TipoHabitacion {
        return TipoHabitacion.entries.firstOrNull { it.nombre == tipoHabitacion }
            ?: TipoHabitacion.SUITE // Valor por defecto si no coincide
    }
}
