package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "plans",
    foreignKeys = [
        ForeignKey(
            entity = Itinerary::class,
            parentColumns = ["id"],
            childColumns = ["itineraryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Plan(
    @PrimaryKey val id: String,
    val itineraryId: String,
    val type: PlanType,
    val name: String,
    val date: Date,
    val details: Map<String, Any>
)
