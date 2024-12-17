package com.jostin.planificadorviaje.data.local.datasource.interfaces

import com.jostin.planificadorviaje.data.model.User

interface UserDataSource {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByEmail(email: String): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun clearUsers()
}