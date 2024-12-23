package com.jostin.planificadorviaje.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import com.google.firebase.firestore.Query
import com.jostin.planificadorviaje.model.AdminHotel
import com.jostin.planificadorviaje.utils.FireStoreCallback

class AdminHotelRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getHotelsByCity(city: String, callback: FireStoreCallback<List<AdminHotel>>) {
        firestore.collection("hoteles")
            .whereEqualTo("ciudad", city)
            .get()
            .addOnSuccessListener { documents ->
                val hotels = documents.map { it.toObject(AdminHotel::class.java) }
                callback.onSuccess(hotels)
            }
            .addOnFailureListener { exception ->
                callback.onError(exception.localizedMessage ?: "Error desconocido")
            }
    }

    fun addHotel(hotel: AdminHotel, callback: FireStoreCallback<Unit>) {
        firestore.collection("hoteles")
            .add(hotel)
            .addOnSuccessListener {
                callback.onSuccess(Unit)
            }
            .addOnFailureListener { exception ->
                callback.onError(exception.localizedMessage ?: "Error al agregar hotel")
            }
    }

    fun getLastHotelId(callback: FireStoreCallback<Int>) {
        firestore.collection("hoteles")
            .orderBy("id", Query.Direction.DESCENDING) // Ordenar por el campo "id" en orden descendente
            .limit(1) // Solo obtener el último documento
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val lastHotel = documents.documents[0]
                    val lastId = lastHotel.getString("id")?.toIntOrNull() ?: 0
                    callback.onSuccess(lastId)
                } else {
                    callback.onSuccess(0) // Si no hay documentos, el primer ID será 1
                }
            }
            .addOnFailureListener { exception ->
                callback.onError(exception.localizedMessage ?: "Error al obtener el último ID")
            }
    }
}
