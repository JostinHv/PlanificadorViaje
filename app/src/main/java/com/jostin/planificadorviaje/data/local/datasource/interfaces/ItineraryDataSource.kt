package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.data.model.Itinerary

interface ItineraryDataSource {
    suspend fun getItineraries(): List<Itinerary>
    suspend fun getItinerary(id: String): Itinerary
    suspend fun createItinerary(itinerary: Itinerary)
    suspend fun updateItinerary(itinerary: Itinerary)
    suspend fun deleteItinerary(id: String)
}