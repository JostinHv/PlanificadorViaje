package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.data.model.Reserva

interface ReservaDataSource {
    suspend fun getReservas(): List<Reserva>
    suspend fun getReserva(id: String): Reserva?
    suspend fun createReserva(reserva: Reserva)
    suspend fun updateReserva(reserva: Reserva)
    suspend fun deleteReserva(id: String)
    suspend fun getReservaByHotelId(hotelId: String): Reserva?
}