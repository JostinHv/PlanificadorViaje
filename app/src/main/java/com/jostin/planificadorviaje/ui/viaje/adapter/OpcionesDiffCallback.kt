package com.jostin.planificadorviaje.ui.viaje.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jostin.planificadorviaje.data.model.OpcionViaje

class OpcionesDiffCallback : DiffUtil.ItemCallback<OpcionViaje>() {
    override fun areItemsTheSame(oldItem: OpcionViaje, newItem: OpcionViaje): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OpcionViaje, newItem: OpcionViaje): Boolean {
        return oldItem == newItem
    }
}
