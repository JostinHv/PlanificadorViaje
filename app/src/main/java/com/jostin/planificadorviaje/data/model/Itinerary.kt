package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity(tableName = "itineraries")
data class Itinerary(
    @DocumentId @PrimaryKey val id: String = "",
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


