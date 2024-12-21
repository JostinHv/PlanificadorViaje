package com.jostin.planificadorviaje.data.local.dao

import androidx.room.*
import com.jostin.planificadorviaje.data.model.Plan

@Dao
interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPlan(plan: Plan)

    @Query("SELECT * FROM plans WHERE itineraryId = :itineraryId")
    suspend fun getPlansForItinerary(itineraryId: String): List<Plan>

    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun getPlan(id: String): Plan

    @Update
    suspend fun updatePlan(plan: Plan)

    @Delete
    suspend fun deletePlan(plan: Plan)

    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun deletePlanById(id: String)

}