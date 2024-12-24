package com.jostin.planificadorviaje.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreUserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val userCollection = firestore.collection("users")

    suspend fun getAllUsers(): List<User> {
        return userCollection.get().await().toObjects(User::class.java)
    }

    suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = userCollection.whereEqualTo("email", email).get().await()
        return querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
    }

    suspend fun createUser(user: User) {
        userCollection.document(user.id).set(user).await()
    }

    suspend fun updateUser(user: User) {
        userCollection.document(user.id).update(
            mapOf(
                "name" to user.name,
                "lastname" to user.lastname,
                "email" to user.email,
                "password" to user.password,
                "role" to user.role,
                "profilePicture" to user.profilePicture
            )
        ).await()
    }

    suspend fun clearUsers() {
        userCollection.get().await().documents.forEach {
            userCollection.document(it.id).delete().await()
        }
    }
}
