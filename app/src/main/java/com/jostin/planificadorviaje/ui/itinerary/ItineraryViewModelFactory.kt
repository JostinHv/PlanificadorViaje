package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jostin.planificadorviaje.data.repository.ItineraryRepository

class ItineraryViewModelFactory(
    private val repository: ItineraryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItineraryViewModel::class.java)) {
            return ItineraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
