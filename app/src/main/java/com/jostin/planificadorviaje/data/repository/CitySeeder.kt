package com.jostin.planificadorviaje.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.data.model.City

class CitySeeder(private val firestore: FirebaseFirestore) {

    private val citiesCollection = "cities"

    fun seedCities(cities: List<City>, callback: (Boolean, String) -> Unit) {
        firestore.collection(citiesCollection).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // Si la colección está vacía, agregar las ciudades
                    val batch = firestore.batch()
                    cities.forEach { city ->
                        val cityRef = firestore.collection(citiesCollection).document(city.id)
                        batch.set(cityRef, city)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            callback(true, "Ciudades añadidas exitosamente")
                        }
                        .addOnFailureListener { exception ->
                            callback(false, "Error al añadir ciudades: ${exception.message}")
                        }
                } else {
                    callback(false, "Ciudades ya existentes en la base de datos")
                }
            }
            .addOnFailureListener { exception ->
                callback(false, "Error al verificar ciudades: ${exception.message}")
            }
    }
}
