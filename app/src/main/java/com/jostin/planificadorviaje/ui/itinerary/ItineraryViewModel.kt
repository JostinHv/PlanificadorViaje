package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(private val itineraryRepository: ItineraryRepository) :
    ViewModel() {

    private val _itinerary = MutableLiveData<List<Itinerary>>()
    val itinerary: LiveData<List<Itinerary>> get() = _itinerary

    fun fetchItineraries() {
        viewModelScope.launch {
            val itineraries = itineraryRepository.getItineraries()
            _itinerary.postValue(itineraries)
        }
    }

}