package com.jostin.planificadorviaje.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.ItineraryWithPlans
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreItineraryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {


    private val itinerariesCollection = firestore.collection("itineraries")
    private val plansCollection = firestore.collection("plans")

    // Obtener todos los itinerarios para un usuario
    suspend fun getItinerariesForUser(userId: String): List<Itinerary> {
        val querySnapshot = itinerariesCollection
            .whereEqualTo("userId", userId)
            .get()
            .await()
        return querySnapshot.documents.mapNotNull { it.toObject<Itinerary>() }
    }

    // Obtener un itinerario específico por ID
    suspend fun getItinerary(itineraryId: String): Itinerary? {
        val documentSnapshot = itinerariesCollection.document(itineraryId).get().await()
        return documentSnapshot.toObject<Itinerary>()
    }

    // Crear un itinerario
    suspend fun createItinerary(itinerary: Itinerary) {
        itinerariesCollection.document(itinerary.id).set(itinerary).await()
    }

    // Actualizar un itinerario existente
    suspend fun updateItinerary(itinerary: Itinerary) {
        itinerariesCollection.document(itinerary.id).set(itinerary).await()
    }

    // Eliminar un itinerario
    suspend fun deleteItinerary(itineraryId: String) {
        itinerariesCollection.document(itineraryId).delete().await()
    }

    // Obtener un itinerario con sus planes
    suspend fun getItineraryWithPlans(itineraryId: String): ItineraryWithPlans? {
        // Obtener el itinerario
        val itinerarySnapshot = itinerariesCollection.document(itineraryId).get().await()
        val itinerary = itinerarySnapshot.toObject<Itinerary>()

        // Verifica si el itinerario existe
        if (itinerary != null) {
            // Obtener los planes asociados al itinerario
            val plansQuery = plansCollection.whereEqualTo("itineraryId", itineraryId).get().await()
            val plans = plansQuery.documents.mapNotNull { it.toObject<Plan>() }

            // Retorna el itinerario con sus planes
            return ItineraryWithPlans(itinerary, plans)
        }
        return null
    }

    suspend fun getItineraries(): List<Itinerary>? {
        return try {
            val querySnapshot = itinerariesCollection.get().await()
            querySnapshot.documents.mapNotNull { it.toObject<Itinerary>() }
        } catch (e: Exception) {
            null
        }
    }
}
