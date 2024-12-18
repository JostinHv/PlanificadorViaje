package com.jostin.planificadorviaje.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jostin.planificadorviaje.data.model.Reserva

@Dao
interface ReservaDao {
    @Query("SELECT * FROM reserva")
    suspend fun getAll(): List<Reserva>

    @Query("SELECT * FROM reserva WHERE id = :id")
    suspend fun getById(id: String): Reserva?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reserva: Reserva)

    @Update
    suspend fun update(reserva: Reserva)

    @Query("DELETE FROM reserva WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM reserva WHERE hotel_id = :hotelId")
    suspend fun getByHotelId(hotelId: String): Reserva
}