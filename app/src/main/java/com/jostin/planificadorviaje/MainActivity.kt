package com.jostin.planificadorviaje

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar la Toolbar como ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar) // Esta línea resuelve el problema

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.itineraryFragment, R.id.accountFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Escuchar cambios en el destino y ajustar la visibilidad del BottomNavigationView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    // Ocultar el BottomNavigationView en el login
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.restaurantMapFragment -> {
                    // Ocultar el BottomNavigationView en el mapa de restaurantes
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.hotelMapFragment -> {
                    // Ocultar el BottomNavigationView en el mapa de hoteles
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.restaurantFormFragment -> {
                    // Ocultar el BottomNavigationView en el formulario de restaurantes
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.hotelFormFragment -> {
                    // Ocultar el BottomNavigationView en el formulario de hoteles
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.selectPlanTypeFragment -> {
                    // Ocultar el BottomNavigationView en el formulario de hoteles
                    bottomNavigationView.visibility = View.GONE
                }


                else -> {
                    // Mostrar el BottomNavigationView en otros fragmentos
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}