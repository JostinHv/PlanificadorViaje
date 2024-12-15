package com.jostin.planificadorviaje.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ItineraryRepository) : ViewModel() {

    private val _upcomingItinerary = MutableLiveData<Itinerary>()
    private val _itineraries = MutableLiveData<List<Itinerary>>()
    val upcomingItinerary: LiveData<Itinerary> get() = _upcomingItinerary
    val itineraries: LiveData<List<Itinerary>> = _itineraries

    init {
        loadItineraries()
    }

    public fun loadItineraries() {
        viewModelScope.launch {
            val sampleItineraries = repository.getItineraries()
            _itineraries.value = sampleItineraries
        }
    }

    fun loadUpcomingItinerary() {
        viewModelScope.launch {
            _upcomingItinerary.value = repository.getUpcomingItinerary()
        }
    }
    fun createItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            repository.createItinerary(itinerary)
            loadItineraries()
        }
    }
}
