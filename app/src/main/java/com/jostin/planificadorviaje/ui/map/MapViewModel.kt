package com.jostin.planificadorviaje.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.City
import com.jostin.planificadorviaje.data.repository.CityRepository
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _citiesWithCoordinates = MutableLiveData<List<City>>()
    val citiesWithCoordinates: LiveData<List<City>> get() = _citiesWithCoordinates

    fun loadDestinationsWithCoordinates() {
        val currentUser = UserSessionManager.getCurrentUser()
        viewModelScope.launch {
            try {
                // Obtiene los itinerarios del usuario actual
                val itineraries = itineraryRepository.getItinerariesForUser(currentUser.id)

                // Extrae los nombres de los destinos
                val destinations = itineraries.map { it.destination }.distinct()

                // Busca las ciudades en base a los destinos
                val cities = mutableListOf<City>()
                for (destination in destinations) {
                    val city = cityRepository.getCityByName(destination)
                    if (city != null) {
                        cities.add(city)
                    }
                }

                // Publica las ciudades con coordenadas en el LiveData
                _citiesWithCoordinates.value = cities
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
