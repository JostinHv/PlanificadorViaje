package com.jostin.planificadorviaje.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.io.Serializable
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
    var type: PlanType,
    val name: String,
    val date: Timestamp = Timestamp.now(),
    val details: Map<String, Any>,
) : Serializable {
    constructor() : this("", "", PlanType.ACTIVITY, "", Timestamp.now(), emptyMap())

    fun getDateAsDate(): Date = date.toDate()
}
