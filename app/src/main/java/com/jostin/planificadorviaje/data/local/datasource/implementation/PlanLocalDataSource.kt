package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.PlanDataSource
import com.jostin.planificadorviaje.data.model.Plan
import javax.inject.Inject

class PlanLocalDataSource @Inject constructor(private val database: AppDatabase) : PlanDataSource {
    override suspend fun getPlansForItinerary(itineraryId: String) =
        database.planDao().getPlansForItinerary(itineraryId)

    override suspend fun getPlan(id: String): Plan {
        return database.planDao().getPlan(id)
    }

    override suspend fun updatePlan(plan: Plan) = database.planDao().updatePlan(plan)
    override suspend fun createPlan(plan: Plan) = database.planDao().createPlan(plan)
    override suspend fun deletePlan(plan: Plan) {
        database.planDao().deletePlan(plan)
    }

    override suspend fun deletePlanById(id: String) = database.planDao().deletePlanById(id)
}
