package com.jostin.planificadorviaje.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.data.model.City
import kotlinx.coroutines.tasks.await

class CityRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getCities(callback: (List<City>) -> Unit) {
        firestore.collection("cities").get().addOnSuccessListener { querySnapshot ->
            val cities = querySnapshot.documents.mapNotNull { document ->
                document.toObject(City::class.java)
            }
            callback(cities)
        }.addOnFailureListener {
            callback(emptyList())
        }
    }

    suspend fun getCityByName(name: String): City? {
        return try {
            val querySnapshot = firestore.collection("cities")
                .whereEqualTo("name", name)
                .get()
                .await()
            if (querySnapshot.documents.isNotEmpty()) {
                querySnapshot.documents[0].toObject(City::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("CityRepository", "Error fetching city: ${e.message}")
            null
        }
    }


    fun getCityById(id: String, callback: (City?) -> Unit) {
        firestore.collection("cities").document(id).get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject(City::class.java)
            callback(city)
        }.addOnFailureListener {
            callback(null)
        }
    }
}
