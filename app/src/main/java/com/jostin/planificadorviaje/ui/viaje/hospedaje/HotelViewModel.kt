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


}
