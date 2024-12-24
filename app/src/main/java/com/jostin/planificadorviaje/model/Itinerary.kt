package com.jostin.planificadorviaje.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Itinerary(
    @DocumentId val id: String = "",
    val name: String = "",
    val userId: String = "",
    val destination: String = "",
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val description: String = "",
    val imageUrl: String = "",
    val sharedWith: List<User> = emptyList(),
    val plans: List<Plan> = emptyList()
)


