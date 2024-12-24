// data/repository/UserRepository.kt
package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.remote.FirestoreUserDataSource
import com.jostin.planificadorviaje.model.User

class UserRepository(
    private val remoteDataSource: FirestoreUserDataSource
) {

    suspend fun getAllUsers(): List<User> {
        val remoteUsers = remoteDataSource.getAllUsers()
        return remoteUsers
    }

    suspend fun updateUser(user: User) {
        remoteDataSource.updateUser(user)
    }

    suspend fun registerUser(user: User): Boolean {
        val existingUser = remoteDataSource.getUserByEmail(user.email)
        return if (existingUser == null) {
            remoteDataSource.createUser(user)
            true
        } else {
            false
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return remoteDataSource.getUserByEmail(email)
    }
}
