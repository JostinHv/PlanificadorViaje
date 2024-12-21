package com.jostin.planificadorviaje.data.local.dao

// data/local/ItineraryDao.kt
import androidx.room.*
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.relations.ItineraryWithPlans

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itineraries")
    suspend fun getAll(): List<Itinerary>

    @Query("SELECT * FROM itineraries WHERE id = :id")
    suspend fun getById(id: String): Itinerary

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itinerary: Itinerary)

    @Update
    suspend fun update(itinerary: Itinerary)

    @Query("DELETE FROM itineraries WHERE id = :id")
    suspend fun deleteById(id: String)

    @Transaction
    @Query("SELECT * FROM itineraries WHERE id = :itineraryId")
    suspend fun getItineraryWithPlans(itineraryId: String): ItineraryWithPlans

}