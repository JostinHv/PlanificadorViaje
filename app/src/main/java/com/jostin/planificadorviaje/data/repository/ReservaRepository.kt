package com.jostin.planificadorviaje.data.repository

import android.util.Log
import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.model.Reserva
import com.jostin.planificadorviaje.utils.SampleDataGenerator

class ReservaRepository(private val localDataSource: LocalDataSource) {

    suspend fun getReservas(): List<Reserva> {
        return localDataSource.getReservas()
    }

    suspend fun getReserva(reservaId: String): Reserva {
        return localDataSource.getReserva(reservaId)
            ?: throw IllegalArgumentException("Reserva not found")
    }

    suspend fun createReserva(reserva: Reserva) {
        localDataSource.createReserva(reserva)
    }

    suspend fun updateReserva(reserva: Reserva) {
        localDataSource.updateReserva(reserva)
    }

    suspend fun deleteReserva(id: String) {
        localDataSource.deleteReserva(id)
    }

    suspend fun initializeSampleData() {
        if (localDataSource.getReservas().isEmpty()) {
            val sampleReservas = SampleDataGenerator.generateSampleReservas()
            sampleReservas.forEach { localDataSource.createReserva(it) }
        }
    }

   suspend fun getReservaByHotelId(hotelId: String): Reserva? {
       Log.d("ReservaRepository", "getReservaByHotelId: $hotelId")
        return localDataSource.getReservaByHotelId(hotelId)
    }
}