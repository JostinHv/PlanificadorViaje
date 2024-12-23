package com.jostin.planificadorviaje.berpi.ui.restaurantes

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jostin.planificadorviaje.berpi.model.AdminRestaurante
import com.jostin.planificadorviaje.berpi.repository.AdminRestauranteRepository
import com.jostin.planificadorviaje.berpi.utils.FireStoreCallback
import com.jostin.planificadorviaje.berpi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AdminRestauranteViewModel @Inject constructor(
    private val repository: AdminRestauranteRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _restaurantes = MutableLiveData<Resource<List<AdminRestaurante>>>()
    val restaurantes: LiveData<Resource<List<AdminRestaurante>>> = _restaurantes

    // Obtener restaurantes por ciudad
    fun fetchRestaurantesByCity(city: String) {
        _restaurantes.postValue(Resource.Loading())
        repository.getRestaurantesByCity(city, object : FireStoreCallback<List<AdminRestaurante>> {
            override fun onSuccess(data: List<AdminRestaurante>) {
                if (data.isEmpty()) {
                    _restaurantes.postValue(Resource.Error("No se encontraron restaurantes"))
                } else {
                    _restaurantes.postValue(Resource.Success(data))
                }
            }

            override fun onError(message: String) {
                _restaurantes.postValue(Resource.Error(message))
            }
        })
    }

    // Agregar un restaurante
    fun addRestaurant(restaurante: AdminRestaurante) {
        repository.addRestaurant(restaurante, object : FireStoreCallback<Unit> {
            override fun onSuccess(data: Unit) {
                Toast.makeText(context, "Restaurante agregado exitosamente", Toast.LENGTH_SHORT).show()
            }

            override fun onError(message: String) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Obtener el último ID de restaurante
    fun getLastRestauranteId(callback: (Int) -> Unit) {
        repository.getLastRestauranteId(object : FireStoreCallback<Int> {
            override fun onSuccess(data: Int) {
                callback(data)
            }

            override fun onError(message: String) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        })
    }
}