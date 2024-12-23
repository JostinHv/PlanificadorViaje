package com.jostin.planificadorviaje.berpi.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jostin.planificadorviaje.berpi.repository.AdminHotelRepository
import com.jostin.planificadorviaje.berpi.utils.Resource
import com.jostin.planificadorviaje.berpi.model.AdminHotel
import com.jostin.planificadorviaje.berpi.utils.FireStoreCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AdminHotelViewModel @Inject constructor(
    private val repository: AdminHotelRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _hotels = MutableLiveData<Resource<List<AdminHotel>>>()
    val hotels: LiveData<Resource<List<AdminHotel>>> = _hotels

    fun fetchHotelsByCity(city: String) {
        _hotels.postValue(Resource.Loading())
        repository.getHotelsByCity(city, object : FireStoreCallback<List<AdminHotel>> {
            override fun onSuccess(data: List<AdminHotel>) {
                if (data.isEmpty()) {
                    _hotels.postValue(Resource.Error("No se encontraron resultados"))
                } else {
                    _hotels.postValue(Resource.Success(data))
                }
            }

            override fun onError(message: String) {
                _hotels.postValue(Resource.Error(message))
            }
        })
    }

    fun addHotel(hotel: AdminHotel) {
        repository.addHotel(hotel, object : FireStoreCallback<Unit> {

            override fun onSuccess(data: Unit) {
                Toast.makeText(context, "Hotel agregado exitosamente", Toast.LENGTH_SHORT).show()
            }

            override fun onError(message: String) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getLastHotelId(callback: (Int) -> Unit) {
        repository.getLastHotelId(object : FireStoreCallback<Int> {
            override fun onSuccess(data: Int) {
                callback(data)
            }

            override fun onError(message: String) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        })
    }
}