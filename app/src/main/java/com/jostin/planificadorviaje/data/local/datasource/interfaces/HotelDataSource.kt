package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.model.Hotel

interface HotelDataSource {
    suspend fun getHotels(): List<Hotel>
    suspend fun getHotel(id: String): Hotel?
    suspend fun createHotel(hotel: Hotel)
    suspend fun updateHotel(hotel: Hotel)
    suspend fun deleteHotel(id: String)
}