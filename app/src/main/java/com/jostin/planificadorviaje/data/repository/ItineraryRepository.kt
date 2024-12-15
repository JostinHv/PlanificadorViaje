package com.jostin.planificadorviaje.data.repository

import android.util.Log
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.utils.SampleDataGenerator

class ItineraryRepository(private val localDataSource: LocalDataSource) {


    suspend fun getItineraries(): List<Itinerary> {
        return localDataSource.getItineraries()
    }

    suspend fun getItinerary(itineraryId: String): Itinerary {
        return localDataSource.getItinerary(itineraryId)
            ?: throw IllegalArgumentException("Itinerary not found")
    }

    suspend fun createItinerary(itinerary: Itinerary) {
        localDataSource.createItinerary(itinerary)
    }

    suspend fun updateItinerary(itinerary: Itinerary) {
        localDataSource.updateItinerary(itinerary)
    }

    suspend fun deleteItinerary(id: String) {
        localDataSource.deleteItinerary(id)
    }

    suspend fun getUpcomingItinerary(): Itinerary? {
        return localDataSource.getItineraries().minByOrNull { it.startDate.time }
    }

    suspend fun getItinerariesForUser(userId: String): List<Itinerary> {
        return localDataSource.getItineraries().filter { it.userId == userId }
    }

    suspend fun initializeSampleData() {
        // Verifica si la base de datos está vacía y si es así, agrega los datos iniciales
        if (localDataSource.getItineraries().isEmpty()) {
            val sampleItineraries = SampleDataGenerator.generateSampleItineraries()
            sampleItineraries.forEach { localDataSource.createItinerary(it) }
        }
    }

    // Opción adicional: Mantén los datos de muestra para propósitos de prueba si lo necesitas
    private fun getSampleItineraries(): List<Itinerary> {
        return SampleDataGenerator.generateSampleItineraries()
    }
}