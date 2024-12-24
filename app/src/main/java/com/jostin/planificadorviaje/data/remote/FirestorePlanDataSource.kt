package com.jostin.planificadorviaje.data.remote

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.PlanType
import kotlinx.coroutines.tasks.await

class FirestorePlanDataSource(private val firestore: FirebaseFirestore) {

    private val plansCollection = firestore.collection("plans")

    suspend fun getPlansForItinerary(itineraryId: String): List<Plan> {
        val querySnapshot = plansCollection
            .whereEqualTo("itineraryId", itineraryId)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { document ->
            val plan = document.toObject(Plan::class.java)
            if (plan != null) {
                plan.copy(date = document.getTimestamp("date") ?: Timestamp.now())
            } else {
                null
            }
        }
    }

    suspend fun getPlan(id: String): Plan {
        val document = plansCollection.document(id).get().await()
        val plan = document.toObject(Plan::class.java)?.apply {
            type = PlanType.fromString(document.getString("type") ?: "ACTIVITY")
        }
        return plan ?: throw Exception("Plan not found")
    }

    suspend fun updatePlan(plan: Plan) {
        val data = plan.toMap()
        plansCollection.document(plan.id).set(data).await()
    }

    suspend fun createPlan(plan: Plan) {
        val data = plan.toMap()
        plansCollection.document(plan.id).set(data).await()
    }

    suspend fun deletePlan(plan: Plan) {
        plansCollection.document(plan.id).delete().await()
    }

    suspend fun deletePlanById(id: String) {
        plansCollection.document(id).delete().await()
    }

    // Extensiones auxiliares para convertir un Plan a un Map y viceversa
    private fun Plan.toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "itineraryId" to itineraryId,
            "type" to PlanType.toString(type),
            "name" to name,
            "date" to date.toDate(), // Almacenar Date como timestamp en Firestore
            "details" to details
        )
    }
}
