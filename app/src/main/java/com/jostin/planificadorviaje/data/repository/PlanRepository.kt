// data/repository/PlanRepository.kt
package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.utils.SampleDataGenerator

class PlanRepository(private val localDataSource: LocalDataSource) {
    private val samplePlans =
        SampleDataGenerator.generateSampleItineraries().flatMap { it.plans }.toMutableList()

    suspend fun getPlansForItinerary(itineraryId: String): List<Plan> {
        return samplePlans.filter { it.itineraryId == itineraryId }
    }

    suspend fun getPlan(id: String): Plan {
        return samplePlans.find { it.id == id }
            ?: throw IllegalArgumentException("Plan not found")
    }

    suspend fun updatePlan(plan: Plan) {
        val index = samplePlans.indexOfFirst { it.id == plan.id }
        if (index != -1) {
            samplePlans[index] = plan
        }
    }

    suspend fun createPlan(plan: Plan) {
        // En una implementación real, insertarías en la base de datos local y/o remota
        samplePlans.add(plan)
    }

    suspend fun deletePlan(id: String) {
        // En una implementación real, eliminarías de la base de datos local y/o remota
        samplePlans.removeAll { it.id == id }
    }
}