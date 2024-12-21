package com.jostin.planificadorviaje.berpi.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jostin.planificadorviaje.berpi.model.AdminRestaurante
import com.jostin.planificadorviaje.berpi.utils.FireStoreCallback
import javax.inject.Inject

class AdminRestauranteRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    // Obtener restaurantes por ciudad
    fun getRestaurantesByCity(city: String, callback: FireStoreCallback<List<AdminRestaurante>>) {
        firestore.collection("restaurantes")
            .whereEqualTo("ciudad", city)
            .get()
            .addOnSuccessListener { documents ->
                val restaurantes = documents.map { it.toObject(AdminRestaurante::class.java) }
                callback.onSuccess(restaurantes)
            }
            .addOnFailureListener { exception ->
                callback.onError(exception.localizedMessage ?: "Error desconocido")
            }
    }

    // Agregar un restaurante
    fun addRestaurant(restaurante: AdminRestaurante, callback: FireStoreCallback<Unit>) {
        firestore.collection("restaurantes")
            .add(restaurante)
            .addOnSuccessListener {
                callback.onSuccess(Unit)
            }
            .addOnFailureListener { exception ->
                callback.onError(exception.localizedMessage ?: "Error al agregar restaurante")
            }
    }

    // Obtener el último ID del restaurante
    fun getLastRestauranteId(callback: FireStoreCallback<Int>) {
        firestore.collection("restaurantes")
            .orderBy("id", Query.Direction.DESCENDING) // Ordenar por el campo "id" en orden descendente
            .limit(1) // Solo obtener el último documento
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val lastRestaurante = documents.documents[0]
                    val lastId = lastRestaurante.getString("id")?.toIntOrNull() ?: 0
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
