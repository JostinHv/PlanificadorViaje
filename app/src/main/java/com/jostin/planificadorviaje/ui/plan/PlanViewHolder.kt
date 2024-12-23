package com.jostin.planificadorviaje.ui.plan

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.ItemPlanBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
class PlanViewHolder(
    private val binding: ItemPlanBinding,
    private val onPlanSelected: (Plan, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    fun bind(plan: Plan, isSelected: Boolean) {
        binding.apply {
            // Set plan name and date
            textViewPlanName.text = plan.name
            textViewPlanDate.text = dateFormatter.format(plan.date)

            // Set the icon based on the plan type
            val (iconResId, backgroundColor) = when (plan.type) {
                PlanType.FLIGHT -> Pair(R.drawable.ic_flight, R.color.white)
                PlanType.ACCOMMODATION -> Pair(R.drawable.ic_hotel, R.color.white)
                PlanType.RESTAURANT -> Pair(R.drawable.ic_restaurant, R.color.white)
                PlanType.ACTIVITY -> Pair(R.drawable.ic_activity, R.color.white)
                PlanType.CAR_RENTAL -> Pair(R.drawable.ic_car, R.color.white)
                PlanType.MEETING -> Pair(R.drawable.ic_meeting, R.color.white)
                PlanType.TRANSPORT -> Pair(R.drawable.ic_transport, R.color.white)
                PlanType.PACKAGE_TRIP -> Pair(
                    R.drawable.ic_package_trip,
                    R.color.white
                )
            }
            imageViewPlanType.setImageResource(iconResId)
            imageViewPlanType.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(itemView.context, backgroundColor))

            // Set cost if available
            val pricePerNight = (plan.details["Precio por Noche"] as? Double) ?: 0.0
            if (pricePerNight != 0.0) {
                textViewPlanCost.text = currencyFormatter.format(pricePerNight)
                textViewPlanCost.visibility = android.view.View.VISIBLE
            } else {
                textViewPlanCost.visibility = android.view.View.GONE
            }

            // Create chips for plan details
            chipGroupPlanDetails.removeAllViews()
            plan.details.forEach { (key, value) ->
                val chip = Chip(itemView.context).apply {
                    text = "$key: $value"
                    isCheckable = false
                    setChipBackgroundColorResource(R.color.white)
                    setTextAppearanceResource(R.style.ChipTextAppearance)
                }
                chipGroupPlanDetails.addView(chip)
            }

            // Configure the CheckBox
            checkboxPlan.setOnCheckedChangeListener(null) // Evita disparar eventos previos
            checkboxPlan.isChecked = isSelected // Configura estado inicial del CheckBox
            checkboxPlan.setOnCheckedChangeListener { _, checked ->
                onPlanSelected(plan, checked) // Notifica cambios al adaptador
            }
        }
    }
}
