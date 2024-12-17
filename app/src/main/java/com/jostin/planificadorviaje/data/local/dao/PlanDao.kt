package com.jostin.planificadorviaje.data.local.dao

import androidx.room.*
import com.jostin.planificadorviaje.data.model.Plan

@Dao
interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plan)

    @Update
    suspend fun update(plan: Plan)

    @Delete
    suspend fun delete(plan: Plan)
}