package com.jostin.planificadorviaje.data.local

import androidx.room.*
import com.jostin.planificadorviaje.data.model.Plan

@Dao
interface PlanDao {
    @Query("SELECT * FROM plans WHERE itineraryId = :itineraryId")
    suspend fun getPlansForItinerary(itineraryId: String): List<Plan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plan)

    @Update
    suspend fun update(plan: Plan)

    @Delete
    suspend fun delete(plan: Plan)
}