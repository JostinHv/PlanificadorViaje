package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.ItineraryDataSource
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.model.ItineraryWithPlans

class ItineraryLocalDataSource(private val database: AppDatabase) : ItineraryDataSource {


    override suspend fun getItineraries(): List<Itinerary> {
        return database.itineraryDao().getAll()
    }

    override suspend fun getItinerary(id: String): Itinerary {
        return database.itineraryDao().getById(id)
    }

    override suspend fun createItinerary(itinerary: Itinerary) {
        database.itineraryDao().insert(itinerary)
    }

    override suspend fun updateItinerary(itinerary: Itinerary) {
        database.itineraryDao().update(itinerary)
    }

    override suspend fun deleteItinerary(id: String) {
        database.itineraryDao().deleteById(id)
    }

    override suspend fun getItineraryWithPlans(itineraryId: String): ItineraryWithPlans {
        return database.itineraryDao().getItineraryWithPlans(itineraryId)
    }

}
