package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.local.datasource.FirestoreItineraryDataSource
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.relations.ItineraryWithPlans

class ItineraryRepository(
    private val remoteDataSource: FirestoreItineraryDataSource
) {

    suspend fun getItinerariesForUser(userId: String): List<Itinerary> {
        return try {
            // Prioriza Firestore
            remoteDataSource.getItinerariesForUser(userId)
        } catch (e: Exception) {
            // Si falla, usa datos locales
            return emptyList()
        }
    }

    suspend fun getItinerary(itineraryId: String): Itinerary {
        return remoteDataSource.getItinerary(itineraryId) ?: throw Exception("Itinerary not found")

    }

    suspend fun createItinerary(itinerary: Itinerary) {
        remoteDataSource.createItinerary(itinerary)
    }

    suspend fun updateItinerary(itinerary: Itinerary) {
        remoteDataSource.updateItinerary(itinerary)
    }

    suspend fun deleteItinerary(itineraryId: String) {
        remoteDataSource.deleteItinerary(itineraryId)
    }

    suspend fun getItineraryWithPlans(itineraryId: String): ItineraryWithPlans {
        return remoteDataSource.getItineraryWithPlans(itineraryId)
            ?: throw Exception("Itinerary not found")

    }

    suspend fun getItineraries(): List<Itinerary>? {
        return remoteDataSource.getItineraries()
    }
}
