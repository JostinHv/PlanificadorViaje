package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import kotlinx.coroutines.launch

class ItineraryDetailViewModel(private val repository: ItineraryRepository) : ViewModel() {

    private val _itinerary = MutableLiveData<Itinerary>()
    val itinerary: LiveData<Itinerary> = _itinerary

    fun loadItineraryDetails(itineraryId: String) {
        viewModelScope.launch {
            val itinerary = repository.getItinerary(itineraryId)
            _itinerary.value = itinerary
        }
    }
}
