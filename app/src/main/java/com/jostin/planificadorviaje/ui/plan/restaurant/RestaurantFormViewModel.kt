package com.jostin.planificadorviaje.ui.plan.restaurant

import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantFormViewModel @Inject constructor(private val planRepository: PlanRepository) : ViewModel() {
    private val _selectedPlace = MutableLiveData<Place?>()
    val selectedPlace: LiveData<Place?> = _selectedPlace

    fun setSelectedPlace(place: Place) {
        _selectedPlace.value = place
    }

    fun clearSelectedPlace() {
        _selectedPlace.value = null
    }

    fun savePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.createPlan(plan)
        }
    }
}