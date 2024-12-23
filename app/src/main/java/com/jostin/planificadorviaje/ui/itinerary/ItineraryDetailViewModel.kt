package com.jostin.planificadorviaje.ui.itinerary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.City
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.relations.ItineraryWithPlans
import com.jostin.planificadorviaje.data.repository.CityRepository
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryDetailViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val planRepository: PlanRepository,
    private val cityRepository: CityRepository,
) : ViewModel() {

    private val _itinerary = MutableLiveData<Itinerary>()
    val itinerary: LiveData<Itinerary> = _itinerary

    fun loadItineraryDetails(itineraryId: String) {
        viewModelScope.launch {
            val itinerary = itineraryRepository.getItinerary(itineraryId)
            _itinerary.value = itinerary
        }
    }


    fun deletePlans(plans: List<Plan>, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                plans.forEach { plan ->
                    planRepository.deletePlan(plan)
                }
                onComplete()
            } catch (e: Exception) {
                Log.e("ItineraryDetailViewModel", "Error deleting plans: ${e.message}")
            }
        }
    }


    fun deleteItinerary(itineraryId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                itineraryRepository.deleteItinerary(itineraryId)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


    fun getItineraryDetails(itineraryId: String): LiveData<ItineraryWithPlans> = liveData {
        val itineraryWithPlans = itineraryRepository.getItinerary(itineraryId)
        val plans = planRepository.getPlansForItinerary(itineraryId)
        emit(ItineraryWithPlans(itineraryWithPlans, plans))
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