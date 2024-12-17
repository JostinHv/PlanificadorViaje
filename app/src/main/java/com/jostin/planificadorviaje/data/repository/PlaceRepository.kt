package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.utils.SampleDataGenerator

class PlaceRepository(private val localDataSource: LocalDataSource) {

    suspend fun getPlaces(): List<Place> {
        return localDataSource.getPlaces()
    }

    suspend fun getPlace(placeId: String): Place {
        return localDataSource.getPlace(placeId)
            ?: throw IllegalArgumentException("Place not found")
    }

    suspend fun createPlace(place: Place) {
        localDataSource.createPlace(place)
    }

    suspend fun updatePlace(place: Place) {
        localDataSource.updatePlace(place)
    }

    suspend fun deletePlace(id: String) {
        localDataSource.deletePlace(id)
    }

    suspend fun initializeSampleData() {
        if (localDataSource.getPlaces().isEmpty()) {
            val samplePlaces = SampleDataGenerator.generateSamplePlaces()
            samplePlaces.forEach { localDataSource.createPlace(it) }
        }
    }
}