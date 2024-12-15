package com.jostin.planificadorviaje.ui.itinerary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*

class CreateItineraryViewModel(private val repository: ItineraryRepository) : ViewModel() {

    fun createItinerary(
        name: String,
        destination: String,
        startDate: Date,
        endDate: Date,
        description: String,
        coverImageURL: String,
        plans: List<Plan>,
        sharedWith: List<User>
    ) {
        val itinerary = Itinerary(
            id = UUID.randomUUID().toString(), // Generar un ID único
            name = name,
            destination = destination,
            startDate = startDate,
            endDate = endDate,
            description = description,
            coverImage = "",
            plans = emptyList(),
            sharedWith = emptyList(),
            coverImageUrl = ""
        )

        //Imprime en consola todos mis itinerarios creados
        viewModelScope.launch {
            repository.createItinerary(itinerary)
//            val itineraries = repository.getItineraries()
//            itineraries.forEach { itinerary ->
//                Log.d("ItineraryLog", "ID: ${itinerary.id}")
//                Log.d("ItineraryLog", "Name: ${itinerary.name}")
//                Log.d("ItineraryLog", "Destination: ${itinerary.destination}")
//                Log.d("ItineraryLog", "Start Date: ${itinerary.startDate}")
//                Log.d("ItineraryLog", "End Date: ${itinerary.endDate}")
//                Log.d("ItineraryLog", "Description: ${itinerary.description}")
//                Log.d("ItineraryLog", "Cover Image: ${itinerary.coverImage}")
//                Log.d("ItineraryLog", "Plans: ${itinerary.plans}")
//                Log.d("ItineraryLog", "Shared With: ${itinerary.sharedWith}")
//                Log.d("ItineraryLog", "Cover Image URL: ${itinerary.coverImageUrl}")
//                Log.d("ItineraryLog", "----------")
//            }
        }
    }
}
