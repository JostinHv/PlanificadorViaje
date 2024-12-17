package com.jostin.planificadorviaje

import android.app.Application
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jostin.planificadorviaje.data.repository.*

@HiltAndroidApp
class PlanificadorViajeApplication : Application() {

    // Inyectar los repositorios proporcionados por Hilt
    @Inject lateinit var itineraryRepository: ItineraryRepository
    @Inject lateinit var placeRepository: PlaceRepository
    @Inject lateinit var hotelRepository: HotelRepository
    @Inject lateinit var reservaRepository: ReservaRepository
    @Inject lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        // Inicializa el manejador de sesiones
        UserSessionManager.init(this)

        // Cargar datos de ejemplo al iniciar la aplicación
        CoroutineScope(Dispatchers.IO).launch {
            initializeSampleData()
        }
    }

    private suspend fun initializeSampleData() {
        // Agregar datos de ejemplo para itinerarios, lugares, hoteles y reservas
        itineraryRepository.initializeSampleData()
        placeRepository.initializeSampleData()
        hotelRepository.initializeSampleData()
        reservaRepository.initializeSampleData()

        // Agregar usuarios de ejemplo
        if (userRepository.getAllUsers().isEmpty()) {
            val sampleUsers = com.jostin.planificadorviaje.utils.SampleDataGenerator.generateSampleUsers()
            sampleUsers.forEach { user -> userRepository.registerUser(user) }
        }
    }
}
