package com.jostin.planificadorviaje.data.local.datasource.implementation

import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.interfaces.UserDataSource
import com.jostin.planificadorviaje.model.User


class UserLocalDataSource(private val database: AppDatabase) : UserDataSource {
    override suspend fun getAllUsers(): List<User> {
        return database.userDao().getAllUsers()
    }

    override suspend fun getUserByEmail(email: String): User? {
        return database.userDao().getUserByEmail(email)
    }

    override suspend fun createUser(user: User) {
        database.userDao().insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        database.userDao().updateUser(user)
    }

    override suspend fun clearUsers() {
        database.userDao().clearUsers()
    }
}