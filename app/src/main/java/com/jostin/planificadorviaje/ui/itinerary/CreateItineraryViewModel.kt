package com.jostin.planificadorviaje.ui.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.User
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateItineraryViewModel @Inject constructor(private val itineraryRepository: ItineraryRepository) :
    ViewModel() {

    fun createItinerary(
        name: String,
        userId: String,
        destination: String,
        startDate: Date,
        endDate: Date,
        description: String,
        plans: List<Plan>,
        sharedWith: List<User>
    ) {
        val itinerary = Itinerary(
            id = UUID.randomUUID().toString(), // Generar un ID único
            name = name,
            userId = userId,
            destination = destination,
            startDate = startDate,
            endDate = endDate,
            description = description,
            plans = plans,
            sharedWith = sharedWith,
            imageUrl = ""
        )

        //Imprime en consola todos mis itinerarios creados
        viewModelScope.launch {
            itineraryRepository.createItinerary(itinerary)
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
