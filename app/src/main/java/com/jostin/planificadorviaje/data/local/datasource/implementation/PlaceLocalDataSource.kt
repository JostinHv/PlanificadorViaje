package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.PlaceDataSource
import com.jostin.planificadorviaje.data.model.Place

class PlaceLocalDataSource(private val database: AppDatabase) : PlaceDataSource {
    override suspend fun getPlaces(): List<Place> {
        return database.placeDao().getAll()
    }

    override suspend fun getPlace(id: String): Place? {
        return database.placeDao().getById(id)
    }

    override suspend fun createPlace(place: Place) {
        database.placeDao().insert(place)
    }

    override suspend fun updatePlace(place: Place) {
        database.placeDao().update(place)
    }

    override suspend fun deletePlace(id: String) {
        database.placeDao().deleteById(id)
    }
}
