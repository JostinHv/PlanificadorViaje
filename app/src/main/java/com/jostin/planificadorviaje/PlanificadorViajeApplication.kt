package com.jostin.planificadorviaje

import android.app.Application
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.data.repository.UserRepository
import com.jostin.planificadorviaje.utils.SampleDataGenerator
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class PlanificadorViajeApplication : Application() {

    // Base de datos (Room)
    private val database by lazy { AppDatabase.getDatabase(this) }

    // Fuente de datos local
    private val localDataSource by lazy { LocalDataSource(database) }

    // Repositorio de itinerarios
    val itineraryRepository by lazy { ItineraryRepository(localDataSource) }

    // Repositorio de usuarios
    val userRepository by lazy { UserRepository(localDataSource) }

    override fun onCreate() {
        super.onCreate()
        // Inicializa datos de ejemplo y limpia la base de datos si es necesario
        CoroutineScope(Dispatchers.IO).launch {
            // Limpiar la base de datos (opcional, solo si necesitas reiniciar los datos)
            database.clearAllTables()

            // Inicializa el manejador de sesiones
            UserSessionManager.init(this@PlanificadorViajeApplication)

            // Inicializa datos de ejemplo para itinerarios y usuarios
            initializeSampleData()
        }
    }

    private suspend fun initializeSampleData() {
        // Agrega itinerarios de ejemplo
        itineraryRepository.initializeSampleData()

        // Agrega usuarios de ejemplo
        if (userRepository.getAllUsers().isEmpty()) {
            val sampleUsers = SampleDataGenerator.generateSampleUsers()
            sampleUsers.forEach { user ->
                userRepository.registerUser(user)
            }
        }
    }
}
