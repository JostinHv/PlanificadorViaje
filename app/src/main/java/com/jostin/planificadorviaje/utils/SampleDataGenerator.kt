package com.jostin.planificadorviaje.utils

import com.jostin.planificadorviaje.data.model.*
import java.util.Date

object SampleDataGenerator {

    fun getCities(): List<City> {
        return listOf(
            City("1", "Lima"),
            City("2", "Cusco"),
            City("3", "Arequipa"),
            City("4", "Trujillo"),
            City("5", "Piura"),
            City("6", "Iquitos"),
            City("7", "Puno"),
            City("8", "Tacna"),
            City("9", "Chiclayo"),
            City("10", "Huancayo")
        )
    }


    fun generateSampleItineraries(): List<Itinerary> {
        return listOf(
            Itinerary(
                id = "1",
                userId = "2",
                name = "Vacaciones en París",
                destination = "París, Francia",
                startDate = Date(),
                endDate = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000),
                description = "Un viaje romántico a la Ciudad de la Luz",
                coverImageUrl = "https://example.com/paris.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "3", name = "Jane Smith", email = "jane@a.com"),
                    User(id = "2", name = "John Doe", email = "john@a.com")
                ),
                coverImage = "https://example.com/paris.jpg"
            ),
            Itinerary(
                id = "2",
                userId = "3",
                name = "Aventura en Tokio",
                destination = "Tokio, Japón",
                startDate = Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000),
                endDate = Date(System.currentTimeMillis() + 40 * 24 * 60 * 60 * 1000),
                description = "Explorando la cultura y tecnología japonesa",
                coverImageUrl = "https://example.com/tokyo.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "2", name = "John Doe", email = "john@a.com")
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

    // Generar Lugares (Places)
    fun generateSamplePlaces(): List<Place> {
        return listOf(
            Place(
                id = "1",
                name = "Hotel París",
                address = "1 Rue de la Paix, 75002 Paris, France",
                latitude = 48.869860,
                longitude = 2.331070
            ),
            Place(
                id = "2",
                name = "Hotel Tokio",
                address = "1 Chome-1-2 Oshiage, Sumida City, Tokyo 131-0045, Japan",
                latitude = 35.710063,
                longitude = 139.8107
            ),
            Place(
                id = "3",
                name = "Hotel Costa del Sol",
                address = "Av. Elmer Faucett, Lima, Peru",
                latitude = -12.0211,
                longitude = -77.1143
            ),
            Place(
                id = "4",
                name = "Hotel New York",
                address = "123 5th Ave, New York, NY, USA",
                latitude = 40.712776,
                longitude = -74.005974
            ),
            Place(
                id = "5",
                name = "Hotel London",
                address = "456 Piccadilly, London, UK",
                latitude = 51.509865,
                longitude = -0.118092
            )

        )
    }

    // Generar Hoteles
    fun generateSampleHotels(): List<Hotel> {
        val places = generateSamplePlaces()
        return listOf(
            Hotel(
                id = "1",
                name = "Hotel París",
                stars = 5.0,
                price_per_person = 200.0,
                price_per_day = 400.0,
                checkIn = "15:00",
                checkOut = "11:00",
                place = places[0],
                image_url = "https://example.com/paris.jpg"
            ),
            Hotel(
                id = "2",
                name = "Hotel Tokio",
                stars = 4.5,
                price_per_person = 150.0,
                price_per_day = 300.0,
                checkIn = "15:00",
                checkOut = "11:00",
                place = places[1],
                image_url = "https://example.com/tokyo.jpg"
            ),
            Hotel(
                id = "3",
                name = "Hotel Costa del Sol",
                stars = 4.0,
                price_per_person = 180.0,
                price_per_day = 350.0,
                checkIn = "14:00",
                checkOut = "12:00",
                place = places[2],
                image_url = "https://example.com/lima.jpg"
            ),
            Hotel(
                id = "4",
                name = "Hotel New York",
                stars = 3.5,
                price_per_person = 250.0,
                price_per_day = 500.0,
                checkIn = "15:00",
                checkOut = "11:00",
                place = places[3],
                image_url = "https://example.com/ny.jpg"
            ),
            Hotel(
                id = "5",
                name = "Hotel London",
                stars = 5.0,
                price_per_person = 220.0,
                price_per_day = 450.0,
                checkIn = "15:00",
                checkOut = "11:00",
                place = places[4],
                image_url = "https://example.com/london.jpg"
            )

        )
    }

    // Generar Usuarios
    fun generateSampleUsers(): List<User> {
        return listOf(
            User("1", "Alice Smith", "alice@a.com", "1234", "https://example.com/alice.jpg"),
            User("2", "John Doe", "john@a.com", "1234", "https://example.com/john.jpg"),
            User("3", "Jane Smith", "jane@a.com", "1234", "https://example.com/jane.jpg")
        )
    }

    // Generar Reservas
    fun generateSampleReservas(): List<Reserva> {
        val hotels = generateSampleHotels()
        val users = generateSampleUsers()

        return listOf(
            Reserva(
                id = "1",
                hotel = hotels[0],
                user = null,
                discount_percentage = 0,
                fechaEntrada = "",
                fechaSalida = "",
                personas = 0,
                tipoHabitacion = TipoHabitacion.SUITE.nombre,
                precioTotal = 0.0
            ),
            Reserva(
                id = "2",
                hotel = hotels[1],
                user = null,
                discount_percentage = 0,
                fechaEntrada = "",
                fechaSalida = "",
                personas = 0,
                tipoHabitacion = TipoHabitacion.DELUXE.nombre,
                precioTotal = 0.0
            ),
            Reserva(
                id = "3",
                hotel = hotels[2],
                user = null,
                discount_percentage = 0,
                fechaEntrada = "",
                fechaSalida = "",
                personas = 0,
                tipoHabitacion = TipoHabitacion.DOBLE.nombre,
                precioTotal = 0.0
            ),
            Reserva(
                id = "4",
                hotel = hotels[3],
                user = null,
                discount_percentage = 0,
                fechaEntrada = "0",
                fechaSalida = "0",
                personas = 0,
                tipoHabitacion = TipoHabitacion.FAMILIAR.nombre,
                precioTotal = 0.0
            ),
            Reserva(
                id = "5",
                hotel = hotels[4],
                user = null,
                discount_percentage = 0,
                fechaEntrada = "",
                fechaSalida = "",
                personas = 0,
                tipoHabitacion = TipoHabitacion.EJECUTIVA.nombre,
                precioTotal = 0.0
            )
        )
    }

}
