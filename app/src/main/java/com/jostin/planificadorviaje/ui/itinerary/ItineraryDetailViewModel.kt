package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.relations.ItineraryWithPlans
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryDetailViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val planRepository: PlanRepository
) : ViewModel() {

    private val _itinerary = MutableLiveData<Itinerary>()
    val itinerary: LiveData<Itinerary> = _itinerary

    fun loadItineraryDetails(itineraryId: String) {
        viewModelScope.launch {
            val itinerary = itineraryRepository.getItinerary(itineraryId)
            _itinerary.value = itinerary
        }
    }

    fun getItineraryDetails(itineraryId: String): LiveData<ItineraryWithPlans> = liveData {
        val itineraryWithPlans = itineraryRepository.getItinerary(itineraryId)
        val plans = planRepository.getPlansForItinerary(itineraryId)
        emit(ItineraryWithPlans(itineraryWithPlans, plans))
    }
}