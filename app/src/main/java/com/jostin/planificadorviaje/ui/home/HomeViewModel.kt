package com.jostin.planificadorviaje.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val itineraryRepository: ItineraryRepository) :
    ViewModel() {

    private val _itineraries = MutableLiveData<List<Itinerary>>()
    val itineraries: LiveData<List<Itinerary>> get() = _itineraries

    private val _upcomingItinerary = MutableLiveData<Itinerary?>()
    val upcomingItinerary: LiveData<Itinerary?> get() = _upcomingItinerary

    init {
        fetchItineraries()
        fetchUpcomingItinerary()
    }

    /**
     * Fetch itineraries for the current user.
     */
    fun fetchItineraries() {
        viewModelScope.launch {
            val currentUser = UserSessionManager.getCurrentUser()
            _itineraries.value = if (currentUser != null) {
                itineraryRepository.getItinerariesForUser(currentUser.id)
            } else {
                emptyList()
            }
        }
    }

    /**
     * Fetch the next upcoming itinerary.
     */
    fun fetchUpcomingItinerary() {
        viewModelScope.launch {
            val upcoming = itineraryRepository.getUpcomingItinerary()
            _upcomingItinerary.value = upcoming
        }
    }

    /**
     * Create a new itinerary and reload the list.
     */
    fun createItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            itineraryRepository.createItinerary(itinerary)
            fetchItineraries()
        }
    }
}
