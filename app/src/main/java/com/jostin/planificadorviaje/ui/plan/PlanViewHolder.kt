package com.jostin.planificadorviaje.ui.plan

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.ItemPlanBinding

class PlanViewHolder(private val binding: ItemPlanBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(plan: Plan) {
        binding.apply {
            textViewPlanName.text = plan.name
            textViewPlanDate.text = plan.date.toString() // Format this as needed

            // Set the icon based on the plan type
            val iconResId = when (plan.type) {
                PlanType.FLIGHT -> R.drawable.ic_flight
                PlanType.ACCOMMODATION -> R.drawable.ic_hotel
                PlanType.RESTAURANT -> R.drawable.ic_restaurant
                PlanType.ACTIVITY -> R.drawable.ic_activity
                // Add other plan types as needed
                PlanType.CAR_RENTAL -> R.drawable.ic_car
                PlanType.MEETING -> R.drawable.ic_meeting
                PlanType.TRANSPORT -> R.drawable.ic_transport
                PlanType.PACKAGE_TRIP -> R.drawable.ic_package_trip
            }
            imageViewPlanType.setImageResource(iconResId)

            // Create chips for plan details
            chipGroupPlanDetails.removeAllViews()
            plan.details.forEach { (key, value) ->
                val chip = Chip(itemView.context)
                chip.text = "$key: $value"
                chipGroupPlanDetails.addView(chip)
            }
        }
    }
}