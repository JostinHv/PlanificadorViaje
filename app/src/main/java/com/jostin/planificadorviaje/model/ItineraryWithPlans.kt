package com.jostin.planificadorviaje.model
import androidx.room.Embedded
import androidx.room.Relation
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.model.Plan

data class ItineraryWithPlans(
    @Embedded val itinerary: Itinerary,
    @Relation(
        parentColumn = "id",
        entityColumn = "itineraryId"
    )
    val plans: List<Plan>
)
