package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jostin.planificadorviaje.data.repository.ItineraryRepository

class ItineraryDetailViewModelFactory(private val repository: ItineraryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItineraryDetailViewModel::class.java)) {
            return ItineraryDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
