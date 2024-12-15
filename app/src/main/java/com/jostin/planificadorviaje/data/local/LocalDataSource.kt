package com.jostin.planificadorviaje.data.local

import android.util.Log
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.User

class LocalDataSource(private val database: AppDatabase) {
    suspend fun getItineraries(): List<Itinerary> {
        return database.itineraryDao().getAll()
    }

    suspend fun getItinerary(id: String): Itinerary {
        return database.itineraryDao().getById(id)
    }

    suspend fun createItinerary(itinerary: Itinerary) {
        database.itineraryDao().insert(itinerary)
    }

    suspend fun updateItinerary(itinerary: Itinerary) {
        database.itineraryDao().update(itinerary)
    }

    suspend fun deleteItinerary(id: String) {
        database.itineraryDao().deleteById(id)
    }

    // Usuarios
    suspend fun getAllUsers(): List<User> {
        return database.userDao().getAllUsers()
    }

    suspend fun getUserByEmail(email: String): User? {
        return database.userDao().getUserByEmail(email)
    }

    suspend fun createUser(user: User) {
        database.userDao().insertUser(user)
    }

    suspend fun updateUser(user: User) {
        database.userDao().updateUser(user)
    }

    suspend fun clearUsers() {
        database.userDao().clearUsers()
    }

}