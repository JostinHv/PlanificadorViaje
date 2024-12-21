// data/repository/PlanRepository.kt
package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.model.Plan

class PlanRepository(private val localDataSource: LocalDataSource) {

    suspend fun getPlansForItinerary(itineraryId: String): List<Plan> {
        return localDataSource.getPlansForItinerary(itineraryId)
    }

    suspend fun getPlan(id: String): Plan {
        return localDataSource.getPlan(id)
    }

    suspend fun updatePlan(plan: Plan) {
        localDataSource.updatePlan(plan)
    }

    suspend fun createPlan(plan: Plan) {
        localDataSource.createPlan(plan)
    }

    suspend fun deletePlanById(id: String) {
        localDataSource.deletePlanById(id)
    }

    suspend fun deletePlan(plan: Plan) {
        localDataSource.deletePlan(plan)
    }


}