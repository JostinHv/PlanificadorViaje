package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "itineraries")
data class Itinerary(
    @PrimaryKey val id: String,
    val name: String,
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val description: String,
    val coverImage: String,
    val plans: List<Plan>,
    val sharedWith: List<User>,
    val coverImageUrl: String
)
