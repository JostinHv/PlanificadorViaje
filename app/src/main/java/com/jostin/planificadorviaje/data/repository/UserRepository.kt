// data/repository/UserRepository.kt
package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.UserDataSource
import com.jostin.planificadorviaje.data.model.User

class UserRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: UserDataSource
) {

    suspend fun getAllUsers(): List<User> {
        val remoteUsers = remoteDataSource.getAllUsers()
        localDataSource.clearUsers()
        remoteUsers.forEach { localDataSource.createUser(it) }
        return remoteUsers
    }

    suspend fun updateUser(user: User) {
        localDataSource.updateUser(user)
        remoteDataSource.updateUser(user)
    }

    suspend fun registerUser(user: User): Boolean {
        val existingUser = remoteDataSource.getUserByEmail(user.email)
        return if (existingUser == null) {
            localDataSource.createUser(user)
            remoteDataSource.createUser(user)
            true
        } else {
            false
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return remoteDataSource.getUserByEmail(email) ?: localDataSource.getUserByEmail(email)
    }
}
