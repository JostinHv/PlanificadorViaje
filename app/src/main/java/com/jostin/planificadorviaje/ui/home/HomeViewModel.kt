package com.jostin.planificadorviaje.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.model.City
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.data.repository.CityRepository
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _itineraries = MutableLiveData<List<Itinerary>>()
    val itineraries: LiveData<List<Itinerary>> = _itineraries

    private val _upcomingItinerary = MutableLiveData<Itinerary?>()
    val upcomingItinerary: LiveData<Itinerary?> = _upcomingItinerary

    fun fetchItineraries() {
        viewModelScope.launch {
            val currentUser = UserSessionManager.getCurrentUser()
            val fetchedItineraries = itineraryRepository.getItinerariesForUser(currentUser.id)
            _itineraries.value = fetchedItineraries
            // Log para verificar los datos obtenidos
            fetchedItineraries.forEach { itinerary ->
            }
            updateUpcomingItinerary(fetchedItineraries)
        }
    }

    private fun updateUpcomingItinerary(itineraries: List<Itinerary>) {
        val currentDate = Calendar.getInstance().time

        // Filtra los itinerarios futuros
        val upcoming = itineraries.filter { it.startDate.after(currentDate) }

        // Selecciona el itinerario más cercano
        _upcomingItinerary.value = upcoming.minByOrNull { it.startDate }
    }

    fun getCityByName(name: String): LiveData<City?> = liveData {
        try {
            val city = cityRepository.getCityByName(name)
            emit(city) // Emitir el resultado al LiveData
        } catch (e: Exception) {
            emit(null) // Emitir null en caso de error
        }
    }

}

