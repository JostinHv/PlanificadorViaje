package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.ReservaDataSource
import com.jostin.planificadorviaje.model.Reserva

class ReservaLocalDataSource(private val database: AppDatabase) : ReservaDataSource {
    override suspend fun getReservas(): List<Reserva> {
        return database.reservaDao().getAll()
    }

    override suspend fun getReserva(id: String): Reserva? {
        return database.reservaDao().getById(id)
    }

    override suspend fun createReserva(reserva: Reserva) {
        database.reservaDao().insert(reserva)
    }

    override suspend fun updateReserva(reserva: Reserva) {
        database.reservaDao().update(reserva)
    }

    override suspend fun deleteReserva(id: String) {
        database.reservaDao().deleteById(id)
    }

    override suspend fun getReservaByHotelId(hotelId: String): Reserva {
        return database.reservaDao().getByHotelId(hotelId)
    }
}