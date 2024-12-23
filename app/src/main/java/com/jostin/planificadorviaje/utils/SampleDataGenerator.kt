package com.jostin.planificadorviaje.utils

import com.jostin.planificadorviaje.model.City
import com.jostin.planificadorviaje.model.Hotel
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.model.Place
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.Reserva
import com.jostin.planificadorviaje.model.TipoHabitacion
import com.jostin.planificadorviaje.model.User
import java.util.Date

object SampleDataGenerator {

    fun getCities(): List<City> {
        return listOf(
            City("1", "Lima", "Lima",-12.0464, -77.0428, "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcSVAXlKq43kq6crj-fH0joQcCsr-QNkcrC1a29a3W1H41-n-OUxUXsx8WZkiaRR310J"),
            City("2", "Cusco", "Cusco", -13.5319, -71.9675, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcTyrHu_KOEMXHW3BQvUO9wbNscTnmEa7z-EX2SyguLFjT8HNK-1Qlo70goCQFrI5POn"),
            City("3", "Arequipa", "Arequipa", -16.4090, -71.5375, "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQ-fzPdipT3HT-j4yjqa6WcKuUg30xBFGn5mOlIZUX0ktMp9rnHG18ok9bgqaHYtC9K"),
            City("4", "Trujillo", "La Libertad", -8.1150, -79.0288, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRxzI2zUK-L2qoLR8HdXAe7UoJfbM6icjh2iyKC6bp84rtK2yRTtAaIN26_6bUmoxD7"),
            City("5", "Piura", "Piura", -5.1945, -80.6328),
            City("6", "Iquitos", "Loreto", -3.7437, -73.2516, "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcT4Zw2VKy6eUl4tSFjPIpH60pT8TV4JLo_VUXHjBoreFfyi4lB56-hpgovA1nUhma3o"),
            City("7", "Puno", "Puno", -15.8402, -70.0219),
            City("8", "Tacna", "Tacna", -18.0066, -70.2463, "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS-_l_WumIp5ktmzO4sFXTa79ameofrXZ1-M997WmI4RroRtX2tY6pOUcReDInkHE2s"),
            City("9", "Chiclayo", "Lambayeque", -6.7766, -79.8440, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRwaRkIlwqfjO_9US2lgwHJPnVPHdTXKoAMHK7fuHhDWhD8UY_uDfR74sjztLxRd8DC"),
            City("10", "Huancayo", "Junín", -12.0651, -75.2049)
        )
    }

    fun getPeruCities(): List<City> {
        return listOf(
            // Amazonas
            City("1", "Chachapoyas", "Amazonas", -6.2317, -77.8690),
            City("2", "Bagua Grande", "Amazonas", -5.7561, -78.4411),
            City("3", "Bagua", "Amazonas", -5.6533, -78.5333),

            // Áncash
            City("4", "Huaraz", "Áncash", -9.5278, -77.5278),
            City("5", "Chimbote", "Áncash", -9.0747, -78.5936),
            City("6", "Casma", "Áncash", -9.4633, -78.2961),
            City("7", "Caraz", "Áncash", -9.0500, -77.8100),

            // Apurímac
            City("8", "Abancay", "Apurímac", -13.6339, -72.8814),
            City("9", "Andahuaylas", "Apurímac", -13.6556, -73.3872),
            City("10", "Chalhuanca", "Apurímac", -14.3000, -73.2500),

            // Arequipa
            City("11", "Arequipa", "Arequipa", -16.4090, -71.5375, "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQ-fzPdipT3HT-j4yjqa6WcKuUg30xBFGn5mOlIZUX0ktMp9rnHG18ok9bgqaHYtC9K"),
            City("12", "Camaná", "Arequipa", -16.6228, -72.7100),
            City("13", "Mollendo", "Arequipa", -17.0231, -72.0147),

            // Ayacucho
            City("14", "Ayacucho", "Ayacucho", -13.1588, -74.2236, "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcTLtAVAvQe8rZIbz0QDysmj99ot-AGb10U43xSlPwovx1hpAe-9ciiJbi9iwIW1Vs_5"),
            City("15", "Huanta", "Ayacucho", -12.9333, -74.2500),
            City("16", "Puquio", "Ayacucho", -14.7000, -74.1333),

            // Cajamarca
            City("17", "Cajamarca", "Cajamarca", -7.1638, -78.5003),
            City("18", "Jaén", "Cajamarca", -5.7081, -78.8047),
            City("19", "Chota", "Cajamarca", -6.5610, -78.6510),

            // Callao
            City("20", "Callao", "Callao", -12.0566, -77.1181, "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcRYwQsaKOiyq56QZxI7K9htEmiNP6sdinmJcHMmO75eiaemV8tjx4v1EsEajg9QTos1"),

            // Cusco
            City("21", "Cusco", "Cusco", -13.5320, -71.9675, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcTyrHu_KOEMXHW3BQvUO9wbNscTnmEa7z-EX2SyguLFjT8HNK-1Qlo70goCQFrI5POn"),
            City("22", "Sicuani", "Cusco", -14.2694, -71.2261),
            City("23", "Quillabamba", "Cusco", -12.8675, -72.6900),
            // Huancavelica
            City(
                "24", "Huancavelica", "Huancavelica", -12.7906, -74.9917
            ),
            City("25", "Pampas", "Huancavelica", -12.4000, -74.8667),

            // Huánuco
            City("26", "Huánuco", "Huánuco", -9.9306, -76.2422),
            City("27", "Tingo María", "Huánuco", -9.2950, -75.9950),

            // Ica
            City("28", "Ica", "Ica", -14.0678, -75.7286),
            City("29", "Chincha Alta", "Ica", -13.4099, -76.1323),
            City("30", "Pisco", "Ica", -13.7100, -76.2032),
            City("31", "Nazca", "Ica", -14.8300, -74.9400),

            // Junín
            City("32", "Huancayo", "Junín", -12.0659, -75.2049),
            City("33", "Tarma", "Junín", -11.4196, -75.6894),
            City("34", "Jauja", "Junín", -11.7750, -75.5000),
            City("35", "La Oroya", "Junín", -11.5333, -75.9000),

            // La Libertad
            City("36", "Trujillo", "La Libertad", -8.1110, -79.0280, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRxzI2zUK-L2qoLR8HdXAe7UoJfbM6icjh2iyKC6bp84rtK2yRTtAaIN26_6bUmoxD7"),
            City("37", "Chepén", "La Libertad", -7.2167, -79.4500),
            City("38", "Pacasmayo", "La Libertad", -7.4000, -79.5667),

            // Lambayeque
            City("39", "Chiclayo", "Lambayeque", -6.7714, -79.8409, "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRwaRkIlwqfjO_9US2lgwHJPnVPHdTXKoAMHK7fuHhDWhD8UY_uDfR74sjztLxRd8DC"),
            City("40", "Lambayeque", "Lambayeque", -6.7011, -79.9061),
            City("41", "Ferreñafe", "Lambayeque", -6.6389, -79.7889),

            // Loreto
            City("42", "Iquitos", "Loreto", -3.7437, -73.2516),
            City("43", "Yurimaguas", "Loreto", -5.9011, -76.1225),
            City("44", "Nauta", "Loreto", -4.5747, -73.7800),

            // Madre de Dios
            City("45", "Puerto Maldonado", "Madre de Dios", -12.5933, -69.1897),
            // Moquegua
            City("46", "Moquegua", "Moquegua", -17.1956, -70.9353),
            City("47", "Ilo", "Moquegua", -17.6394, -71.3375),
            City("48", "Omate", "Moquegua", -16.6556, -71.6583),

            // Pasco
            City("49", "Cerro de Pasco", "Pasco", -10.6825, -76.2567),
            City("50", "Oxapampa", "Pasco", -10.5741, -75.4018),
            City("51", "Villa Rica", "Pasco", -10.7375, -75.2708),

            // Piura
            City("52", "Piura", "Piura", -5.1945, -80.6328),
            City("53", "Sullana", "Piura", -4.9039, -80.6853),
            City("54", "Talara", "Piura", -4.5772, -81.2719),
            City("55", "Paita", "Piura", -5.0892, -81.1144),
            City("56", "Chulucanas", "Piura", -5.0925, -80.1625),

            // Puno
            City("57", "Puno", "Puno", -15.8402, -70.0219),
            City("58", "Juliaca", "Puno", -15.4997, -70.1333),
            City("59", "Ilave", "Puno", -16.0833, -69.6667),
            City("60", "Ayaviri", "Puno", -14.8861, -70.5883),

            // San Martín
            City("61", "Moyobamba", "San Martín", -6.0346, -76.9717),
            City("62", "Tarapoto", "San Martín", -6.4860, -76.3650),
            City("63", "Juanjuí", "San Martín", -7.1761, -76.7275),
            City("64", "Rioja", "San Martín", -6.0589, -77.1667),

            // Tacna
            City("65", "Tacna", "Tacna", -18.0139, -70.2516, "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS-_l_WumIp5ktmzO4sFXTa79ameofrXZ1-M997WmI4RroRtX2tY6pOUcReDInkHE2s"),
            City("66", "Tarata", "Tacna", -17.4761, -70.0325),

            // Tumbes
            City("67", "Tumbes", "Tumbes", -3.5669, -80.4515),
            City("68", "Zarumilla", "Tumbes", -3.5033, -80.2733),
            City("69", "Zorritos", "Tumbes", -3.6800, -80.6683),
            // Lima
            City("70", "Lima", "Lima", -12.0464, -77.0428, "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS-_l_WumIp5ktmzO4sFXTa79ameofrXZ1-M997WmI4RroRtX2tY6pOUcReDInkHE2s"),
            City("71", "Huacho", "Lima", -11.1067, -77.6050),
            City("72", "Huaral", "Lima", -11.4950, -77.2086),
            City("73", "Cañete", "Lima", -13.0750, -76.3833),
            City("74", "Barranca", "Lima", -10.7500, -77.7667),
            City("75", "Chancay", "Lima", -11.5717, -77.2700),
            City("76", "San Vicente de Cañete", "Lima", -13.0750, -76.3833),

            // Ucayali
            City("77", "Pucallpa", "Ucayali", -8.3791, -74.5539),
            City("78", "Atalaya", "Ucayali", -10.7333, -73.7667),
            City("79", "Aguaytía", "Ucayali", -9.0297, -75.5078),

            // Otros departamentos no cubiertos anteriormente

            // Pasco
            City("80", "Cerro de Pasco", "Pasco", -10.6825, -76.2567),
            City("81", "Oxapampa", "Pasco", -10.5741, -75.4018),
            City("82", "Villa Rica", "Pasco", -10.7375, -75.2708),

            // San Martín
            City("83", "Moyobamba", "San Martín", -6.0346, -76.9717),
            City("84", "Tarapoto", "San Martín", -6.4860, -76.3650, "https://www.casa-andina.com/es/destinos/tarapoto"),
            City("85", "Juanjuí", "San Martín", -7.1761, -76.7275),
            City("86", "Rioja", "San Martín", -6.0589, -77.1667),

            // Tumbes
            City("87", "Tumbes", "Tumbes", -3.5669, -80.4515),
            City("88", "Zarumilla", "Tumbes", -3.5033, -80.2733),
            City("89", "Zorritos", "Tumbes", -3.6800, -80.6683),
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
                imageUrl = "https://example.com/paris.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "3", name = "Jane Smith", email = "jane@a.com"),
                    User(id = "2", name = "John Doe", email = "john@a.com")
                )
            ),
            Itinerary(
                id = "2",
                userId = "3",
                name = "Aventura en Tokio",
                destination = "Tokio, Japón",
                startDate = Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000),
                endDate = Date(System.currentTimeMillis() + 40 * 24 * 60 * 60 * 1000),
                description = "Explorando la cultura y tecnología japonesa",
                imageUrl = "https://example.com/tokyo.jpg",
                plans = generateSamplePlans(),
                sharedWith = listOf(
                    User(id = "2", name = "John Doe", email = "john@a.com")
                )
            )
        )
    }

    private fun generateSamplePlans(): List<Plan> {
        return listOf(
//            Plan(
//                id = "1",
//                type = PlanType.FLIGHT,
//                name = "Vuelo de ida",
//                date = Date(),
//                details = mapOf(
//                    "airline" to "Air France",
//                    "flightNumber" to "AF1234",
//                    "departureTime" to "10:00",
//                    "arrivalTime" to "12:00"
//                ),
//                itineraryId = "1"
//            ),
//            Plan(
//                id = "2",
//                type = PlanType.ACCOMMODATION,
//                name = "Hotel Le Grand",
//                date = Date(),
//                details = mapOf(
//                    "checkIn" to "15:00",
//                    "checkOut" to "11:00",
//                    "address" to "1 Rue de la Paix, 75002 Paris, France"
//                ),
//                itineraryId = "1"
//            )
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
            User("1", "Alice", "Smith", "alice@a.com", "1234","Usuario", "https://example.com/alice.jpg"),
            User("2", "John", "Doe","john@a.com", "1234","Usuario", "https://example.com/john.jpg"),
            User("3", "Jane","Smith", "jane@a.com", "1234","Usuario", "https://example.com/jane.jpg")
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
