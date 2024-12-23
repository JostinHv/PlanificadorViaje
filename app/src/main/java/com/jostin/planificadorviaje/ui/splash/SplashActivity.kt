package com.jostin.planificadorviaje.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.jostin.planificadorviaje.MainActivity
import com.jostin.planificadorviaje.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    // Variable para ViewBinding
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Opcional: Puedes utilizar inyecciones de Hilt si las necesitas
        // Ejemplo: @Inject lateinit var someDependency: SomeDependency

        // Simular un tiempo de espera para la Splash o ejecutar alguna lógica
        Handler(Looper.getMainLooper()).postDelayed({
            // Navegar a la MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la SplashActivity
        }, 2000) // 1 segundo de espera, por ejemplo

        // Si el usuario toca la imagen, puede ir a la MainActivity de inmediato
//        binding.introBtn.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish() // Cierra SplashActivity
//        }
    }
}