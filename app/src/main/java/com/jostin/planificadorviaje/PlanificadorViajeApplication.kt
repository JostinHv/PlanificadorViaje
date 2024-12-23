package com.jostin.planificadorviaje

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jostin.planificadorviaje.data.repository.*
import com.jostin.planificadorviaje.utils.SampleDataGenerator

@HiltAndroidApp
class PlanificadorViajeApplication : Application() {

    // Inyectar los repositorios proporcionados por Hilt
    @Inject
    lateinit var itineraryRepository: ItineraryRepository

    @Inject
    lateinit var placeRepository: PlaceRepository

    @Inject
    lateinit var hotelRepository: HotelRepository

    @Inject
    lateinit var reservaRepository: ReservaRepository

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        // Inicializa el manejador de sesiones
        UserSessionManager.init(this)

        // Cargar datos de ejemplo al iniciar la aplicación
        CoroutineScope(Dispatchers.IO).launch {
//            initializeSampleData()
        }

//        val firestore = FirebaseFirestore.getInstance()
//        val citySeeder = CitySeeder(firestore)
//        // Llamar al método de inserción de ciudades
//        citySeeder.seedCities(SampleDataGenerator.getPeruCities()) { success, message ->
//            if (success) {
//                println("Seed: $message")
//            } else {
//                println("Seed Error: $message")
//            }
//        }
    }

    private suspend fun initializeSampleData() {
        // Agregar datos de ejemplo para itinerarios, lugares, hoteles y reservas
        //itineraryRepository.initializeSampleData()
        //placeRepository.initializeSampleData()
        //hotelRepository.initializeSampleData()
        // reservaRepository.initializeSampleData()

        // Agregar usuarios de ejemplo
        if (userRepository.getAllUsers().isEmpty()) {
            val sampleUsers =
                SampleDataGenerator.generateSampleUsers()
            sampleUsers.forEach { user -> userRepository.registerUser(user) }
        }
    }
}
