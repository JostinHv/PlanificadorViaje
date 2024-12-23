package com.jostin.planificadorviaje.utils

import android.content.Context
import com.jostin.planificadorviaje.model.User

object UserSessionManager {

    private const val PREF_NAME = "UserSession"
    private const val KEY_ID = "userId"
    private const val KEY_NAME = "userName"
    private const val KEY_EMAIL = "userEmail"
    private const val KEY_ROLE = "userRole"
    private const val KEY_PROFILE_PICTURE = "userProfilePicture"
    private const val KEY_PASSWORD = "userPassword"

    private lateinit var currentUser: User

    // Inicializar los datos del usuario desde SharedPreferences
    fun init(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id = sharedPreferences.getString(KEY_ID, null)
        val name = sharedPreferences.getString(KEY_NAME, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        val role = sharedPreferences.getString(KEY_ROLE, null)
        val profilePicture = sharedPreferences.getString(KEY_PROFILE_PICTURE, null)

        if (id != null && name != null && email != null) {
            currentUser = User(id, name, email, profilePicture ?: "")
        }
    }

    // Guardar sesión de usuario
    fun saveUser(context: Context, user: User) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_ID, user.id)
            putString(KEY_NAME, user.name)
            putString(KEY_EMAIL, user.email)
            putString(KEY_ROLE, user.role)
            putString(KEY_PROFILE_PICTURE, user.profilePicture)
            putString(KEY_PASSWORD, user.password)
            apply()
        }
        currentUser = user
    }

    // Obtener el usuario actual
    fun getCurrentUser(): User {
        return currentUser
    }

    // Eliminar la sesión del usuario
    fun clearUserSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        currentUser = User("", "", "")
    }

    // Verificar si hay un usuario autenticado
    fun isLoggedIn(): Boolean {
        return currentUser != User("", "", "")
    }
}
