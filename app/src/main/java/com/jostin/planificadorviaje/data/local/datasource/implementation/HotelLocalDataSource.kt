package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.HotelDataSource
import com.jostin.planificadorviaje.model.Hotel


class HotelLocalDataSource(private val database: AppDatabase) : HotelDataSource {
    override suspend fun getHotels(): List<Hotel> {
        return database.hotelDao().getAll()
    }

    override suspend fun getHotel(id: String): Hotel? {
        return database.hotelDao().getById(id)
    }

    override suspend fun createHotel(hotel: Hotel) {
        database.hotelDao().insert(hotel)
    }

    override suspend fun updateHotel(hotel: Hotel) {
        database.hotelDao().update(hotel)
    }

    override suspend fun deleteHotel(id: String) {
        database.hotelDao().deleteById(id)
    }
}