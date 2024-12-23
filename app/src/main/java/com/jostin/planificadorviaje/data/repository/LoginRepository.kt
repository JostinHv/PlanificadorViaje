package com.jostin.planificadorviaje.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    // Validar usuario por correo y contraseña
    suspend fun validateUser(email: String, password: String): Pair<String?, String?> {
        val querySnapshot = userCollection
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .await()
        val user = querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
        return if (user != null) {
            val role = if (user.role == "Admin") "Admin" else "Usuario"
            role to user.name
        } else {
            null to null
        }
    }

    // Registrar un nuevo usuario
    suspend fun registerUser(user: User): Boolean {
        val existingUserQuery = userCollection.whereEqualTo("email", user.email).get().await()
        val existingUser = existingUserQuery.documents.firstOrNull()?.toObject(User::class.java)

        return if (existingUser == null) {
            userCollection.document(user.id).set(user).await()
            true
        } else {
            false
        }
    }

    // Obtener usuario por correo
    suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = userCollection.whereEqualTo("email", email).get().await()
        return querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
    }
}
