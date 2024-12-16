package com.jostin.planificadorviaje.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel(private val repository: PlanRepository) : ViewModel() {
    private val _plan = MutableLiveData<Plan>()
    val plan: LiveData<Plan> = _plan

    fun loadPlan(id: String) {
        viewModelScope.launch {
            _plan.value = repository.getPlan(id)
        }
    }

    fun updatePlan(plan: Plan) {
        viewModelScope.launch {
            repository.updatePlan(plan)
            _plan.value = plan
        }
    }
}