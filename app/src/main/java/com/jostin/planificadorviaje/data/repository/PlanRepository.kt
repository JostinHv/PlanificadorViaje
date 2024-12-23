package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.FirestorePlanDataSource
import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.PlanDataSource
import com.jostin.planificadorviaje.data.model.Plan

class PlanRepository(
    private val remoteDataSource: FirestorePlanDataSource
) {

    suspend fun getPlansForItinerary(itineraryId: String): List<Plan> {
        val remotePlans = remoteDataSource.getPlansForItinerary(itineraryId)
        return remotePlans
    }

    suspend fun getPlan(id: String): Plan {
        return remoteDataSource.getPlan(id)
    }

    suspend fun updatePlan(plan: Plan) {
        remoteDataSource.updatePlan(plan)
    }

    suspend fun createPlan(plan: Plan) {
        remoteDataSource.createPlan(plan)
    }

    suspend fun deletePlanById(id: String) {
        remoteDataSource.deletePlanById(id)
    }

    suspend fun deletePlan(plan: Plan) {
        remoteDataSource.deletePlan(plan)
    }
}
