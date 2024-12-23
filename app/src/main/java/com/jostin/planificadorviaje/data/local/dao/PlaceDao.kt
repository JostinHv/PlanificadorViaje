package com.jostin.planificadorviaje.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jostin.planificadorviaje.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places")
    suspend fun getAll(): List<Place>

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getById(id: String): Place?

    @Insert
    suspend fun insert(place: Place)

    @Update
    suspend fun update(place: Place)

    @Query("DELETE FROM places WHERE id = :id")
    suspend fun deleteById(id: String)
}