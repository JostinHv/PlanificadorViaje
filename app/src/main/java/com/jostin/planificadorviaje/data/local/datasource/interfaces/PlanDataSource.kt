package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.model.Plan


interface PlanDataSource {
    suspend fun getPlansForItinerary(itineraryId: String): List<Plan>
    suspend fun getPlan(id: String): Plan
    suspend fun updatePlan(plan: Plan)
    suspend fun createPlan(plan: Plan)
    suspend fun deletePlan(plan: Plan)
    suspend fun deletePlanById(id: String)
}