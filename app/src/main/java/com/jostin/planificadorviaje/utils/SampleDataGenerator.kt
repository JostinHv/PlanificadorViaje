package com.jostin.planificadorviaje.utils

import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.data.model.User
import java.util.*

object SampleDataGenerator {
    fun generateSampleItineraries(): List<Itinerary> {
        return listOf(
            Itinerary(
                id = "1",
                name = "Vacaciones en París",
                destination = "París, Francia",
                startDate = Date(),
                endDate = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000),
                description = "Un viaje romántico a la Ciudad de la Luz",
                coverImageUrl = "https://example.com/paris.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "2", name = "Jane Smith", email = "jane.smith@example.com"),
                    User(id = "3", name = "John Doe", email = "john.doe@example.com")
                ),
                coverImage = "https://example.com/paris.jpg"
            ),
            Itinerary(
                id = "2",
                name = "Aventura en Tokio",
                destination = "Tokio, Japón",
                startDate = Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000),
                endDate = Date(System.currentTimeMillis() + 40 * 24 * 60 * 60 * 1000),
                description = "Explorando la cultura y tecnología japonesa",
                coverImageUrl = "https://example.com/tokyo.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "2", name = "Jane Smith", email = "jane.smith@example.com")
                ),
                coverImage = "https://example.com/tokyo.jpg"
            )
        )
    }

    private fun generateSamplePlans(): List<Plan> {
        return listOf(
            Plan(
                id = "1",
                type = PlanType.FLIGHT,
                name = "Vuelo de ida",
                date = Date(),
                details = mapOf(
                    "airline" to "Air France",
                    "flightNumber" to "AF1234",
                    "departureTime" to "10:00",
                    "arrivalTime" to "12:00"
                ),
                itineraryId = "1"
            ),
            Plan(
                id = "2",
                type = PlanType.ACCOMMODATION,
                name = "Hotel Le Grand",
                date = Date(),
                details = mapOf(
                    "checkIn" to "15:00",
                    "checkOut" to "11:00",
                    "address" to "1 Rue de la Paix, 75002 Paris, France"
                ),
                itineraryId = "1"
            )
        )
    }

    fun generateSampleUsers(): List<User> {
        return listOf(
            User(
                id = "1",
                name = "Alice Smith",
                email = "alice@a.com",
                password = "1234",
                profilePicture = "https://example.com/alice.jpg"
            ),
            User(
                id = "2",
                name = "John Doe",
                email = "john@a.com",
                password = "1234",
                profilePicture = "https://example.com/john.jpg"
            ),
            User(
                id = "3",
                name = "Jane Smith",
                email = "jane@a.com",
                password = "1234",
                profilePicture = "https://example.com/jane.jp"
            )
        )
    }
}
