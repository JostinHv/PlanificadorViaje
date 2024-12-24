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

@HiltAndroidApp
class PlanificadorViajeApplication : Application() {

    // Inyectar los repositorios proporcionados por Hilt
    @Inject
    lateinit var itineraryRepository: ItineraryRepository

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        // Inicializa el manejador de sesiones
        UserSessionManager.init(this)

    }

}
