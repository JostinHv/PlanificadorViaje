package com.jostin.planificadorviaje.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jostin.planificadorviaje.model.Hotel

@Dao
interface HotelDao {
    @Query("SELECT * FROM hotels")
    suspend fun getAll(): List<Hotel>

    @Query("SELECT * FROM hotels WHERE id = :id")
    suspend fun getById(id: String): Hotel?

    @Insert
    suspend fun insert(hotel: Hotel)

    @Update
    suspend fun update(hotel: Hotel)

    @Query("DELETE FROM hotels WHERE id = :id")
    suspend fun deleteById(id: String)
}