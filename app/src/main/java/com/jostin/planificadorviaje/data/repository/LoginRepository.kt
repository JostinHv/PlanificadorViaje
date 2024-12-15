package com.jostin.planificadorviaje.data.repository

import com.jostin.planificadorviaje.data.local.UserDao
import com.jostin.planificadorviaje.data.model.User
import javax.inject.Inject

class LoginRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun validateUser(email: String, password: String): Pair<String?, String?> {
        val user = userDao.validateUser(email, password)
        return if (user != null) {
            val role = if (user.role == "admin") "admin" else "usuario"
            role to user.name
        } else {
            null to null
        }
    }


    suspend fun registerUser(user: User): Boolean {
        val existingUser = userDao.getUserByEmail(user.email)
        return if (existingUser == null) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    suspend fun getUserByEmail(email: String): User {
        return userDao.getUserByEmail(email)!!
    }
}
