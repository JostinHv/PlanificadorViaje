package com.jostin.planificadorviaje.ui.viaje.resumen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.jostin.planificadorviaje.model.Reserva
import com.jostin.planificadorviaje.data.repository.HotelRepository
import com.jostin.planificadorviaje.data.repository.ReservaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumenViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val reservaRepository: ReservaRepository
) : ViewModel() {

    // Flujo de estado para la reserva seleccionada
    private val _reserva = MutableStateFlow<Reserva?>(null)
    val reserva: StateFlow<Reserva?> get() = _reserva


    fun cargarReserva(reservaId: String) {
        viewModelScope.launch {
            try {
                val reserva = reservaRepository.getReserva(reservaId)
                _reserva.value = reserva
            } catch (e: Exception) {
                e.printStackTrace() // Manejo de errores
            }
        }
    }

    fun obtenerLatLng(): LatLng? {
        val place = _reserva.value?.hotel?.place
        return place?.let { LatLng(it.latitude, it.longitude) }
    }

}