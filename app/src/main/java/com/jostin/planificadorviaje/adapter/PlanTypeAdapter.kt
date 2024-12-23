package com.jostin.planificadorviaje.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jostin.planificadorviaje.model.PlanType
import com.jostin.planificadorviaje.databinding.ItemPlanTypeBinding

class PlanTypeAdapter(
    private val planTypes: List<PlanType>,
    private val onItemClick: (PlanType) -> Unit
) : RecyclerView.Adapter<PlanTypeAdapter.PlanTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanTypeViewHolder {
        val binding = ItemPlanTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanTypeViewHolder, position: Int) {
        holder.bind(planTypes[position])
    }

    override fun getItemCount() = planTypes.size

    inner class PlanTypeViewHolder(private val binding: ItemPlanTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planType: PlanType) {
            binding.planTypeIcon.setImageResource(planType.iconRes)
            binding.planTypeName.text = binding.root.context.getString(planType.nameRes)
            binding.root.setOnClickListener { onItemClick(planType) }
        }
    }
}