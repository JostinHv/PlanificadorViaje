package com.jostin.planificadorviaje.ui.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.databinding.ItemPlanBinding

class PlanAdapter(private val plans: List<Plan>) :
    RecyclerView.Adapter<PlanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(plans[position])
    }

    override fun getItemCount() = plans.size
}