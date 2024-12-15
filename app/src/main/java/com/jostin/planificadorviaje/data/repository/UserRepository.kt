// data/repository/UserRepository.kt
package com.jostin.planificadorviaje.data.repository

import android.content.Context
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.utils.SampleDataGenerator
import com.jostin.planificadorviaje.utils.UserSessionManager

class UserRepository(private val localDataSource: LocalDataSource) {

    suspend fun getAllUsers(): List<User> {
        return localDataSource.getAllUsers()
    }
    suspend fun updateUser(user: User) {
        localDataSource.updateUser(user)
    }

    suspend fun registerUser(user: User): Boolean {
        val existingUser = localDataSource.getUserByEmail(user.email)
        return if (existingUser == null) {
            localDataSource.createUser(user)
            true
        } else {
            false
        }
    }
    suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getUserByEmail(email)
    }
}
