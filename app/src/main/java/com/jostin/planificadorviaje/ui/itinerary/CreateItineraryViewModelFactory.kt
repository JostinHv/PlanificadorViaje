package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jostin.planificadorviaje.data.repository.ItineraryRepository

class CreateItineraryViewModelFactory(private val repository: ItineraryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateItineraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateItineraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
