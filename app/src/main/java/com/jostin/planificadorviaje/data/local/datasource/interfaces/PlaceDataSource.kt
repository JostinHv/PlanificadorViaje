package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.data.model.Place

interface PlaceDataSource {
    suspend fun getPlaces(): List<Place>
    suspend fun getPlace(id: String): Place?
    suspend fun createPlace(place: Place)
    suspend fun updatePlace(place: Place)
    suspend fun deletePlace(id: String)
}