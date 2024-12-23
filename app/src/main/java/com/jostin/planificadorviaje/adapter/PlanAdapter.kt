package com.jostin.planificadorviaje.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.databinding.ItemPlanBinding
import com.jostin.planificadorviaje.ui.plan.PlanViewHolder

class PlanAdapter(
    private var plans: List<Plan>,
    private val onPlanSelected: (Plan, Boolean) -> Unit
) : RecyclerView.Adapter<PlanViewHolder>() {

    private val selectedPlans = mutableListOf<Plan>()

    fun updatePlans(newPlans: List<Plan>) {
        plans = newPlans
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding) { plan, isSelected ->
            if (isSelected) {
                selectedPlans.add(plan)
            } else {
                selectedPlans.remove(plan)
            }
            onPlanSelected(plan, isSelected) // Notifica cambios al fragmento
        }
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        holder.bind(plan, selectedPlans.contains(plan)) // Indica si está seleccionado
    }

    fun getSelectedPlans(): List<Plan> = selectedPlans.toList()

    override fun getItemCount() = plans.size
}

