package com.jostin.planificadorviaje.data.local.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.data.local.datasource.interfaces.UserDataSource
import com.jostin.planificadorviaje.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreUserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserDataSource {
    private val userCollection = firestore.collection("users")

    override suspend fun getAllUsers(): List<User> {
        return userCollection.get().await().toObjects(User::class.java)
    }

    override suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = userCollection.whereEqualTo("email", email).get().await()
        return querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
    }

    override suspend fun createUser(user: User) {
        userCollection.document(user.id).set(user).await()
    }

    override suspend fun updateUser(user: User) {
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

    override suspend fun clearUsers() {
        userCollection.get().await().documents.forEach {
            userCollection.document(it.id).delete().await()
        }
    }
}
