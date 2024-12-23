package com.jostin.planificadorviaje.ui.plan.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.model.Place
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HotelFormViewModel @Inject constructor(private val planRepository: PlanRepository) :
    ViewModel() {

    private val _selectedPlace = MutableLiveData<Place>()
    val selectedPlace: LiveData<Place> = _selectedPlace

    private val _checkInDate = MutableLiveData<Date>()
    val checkInDate: LiveData<Date> = _checkInDate

    private val _checkOutDate = MutableLiveData<Date>()
    val checkOutDate: LiveData<Date> = _checkOutDate

    fun setSelectedPlace(place: Place) {
        _selectedPlace.value = place
    }

    fun setCheckInDate(date: Date) {
        _checkInDate.value = date
    }

    fun setCheckOutDate(date: Date) {
        _checkOutDate.value = date
    }

    fun validateDates(): Boolean {
        val checkIn = checkInDate.value
        val checkOut = checkOutDate.value
        return when {
            checkIn == null || checkOut == null -> false
            checkOut.before(checkIn) -> false
            else -> true
        }
    }

    fun savePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.createPlan(plan)
        }
    }
}

