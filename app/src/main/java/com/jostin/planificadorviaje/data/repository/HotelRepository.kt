package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.model.Hotel
import com.jostin.planificadorviaje.utils.SampleDataGenerator

class HotelRepository(private val localDataSource: LocalDataSource) {

    suspend fun getHotels(): List<Hotel> {
        return localDataSource.getHotels()
    }

    suspend fun getHotel(hotelId: String): Hotel {
        return localDataSource.getHotel(hotelId)
            ?: throw IllegalArgumentException("Hotel not found")
    }

    suspend fun createHotel(hotel: Hotel) {
        localDataSource.createHotel(hotel)
    }

    suspend fun updateHotel(hotel: Hotel) {
        localDataSource.updateHotel(hotel)
    }

    suspend fun deleteHotel(id: String) {
        localDataSource.deleteHotel(id)
    }

    suspend fun initializeSampleData() {
        if (localDataSource.getHotels().isEmpty()) {
            val sampleHotels = SampleDataGenerator.generateSampleHotels()
            sampleHotels.forEach { localDataSource.createHotel(it) }
        }
    }
}