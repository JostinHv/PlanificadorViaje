package com.jostin.planificadorviaje.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val planRepository: PlanRepository
) : ViewModel() {

    fun savePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.createPlan(plan)
        }
    }
}
