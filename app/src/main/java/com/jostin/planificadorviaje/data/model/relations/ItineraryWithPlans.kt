package com.jostin.planificadorviaje.data.model.relations
import androidx.room.Embedded
import androidx.room.Relation
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan

data class ItineraryWithPlans(
    @Embedded val itinerary: Itinerary,
    @Relation(
        parentColumn = "id",
        entityColumn = "itineraryId"
    )
    val plans: List<Plan>
)
