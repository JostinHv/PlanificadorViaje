package com.jostin.planificadorviaje.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jostin.planificadorviaje.data.model.OpcionViaje
import com.jostin.planificadorviaje.databinding.ItemOpcionViajeBinding

class OpcionesAdapter(private val onItemClick: (OpcionViaje) -> Unit) :
    ListAdapter<OpcionViaje, OpcionesAdapter.OpcionViewHolder>(OpcionesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpcionViewHolder {
        val binding = ItemOpcionViajeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OpcionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OpcionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OpcionViewHolder(private val binding: ItemOpcionViajeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(opcion: OpcionViaje) {
            binding.tvHoraSalida.text = opcion.horaSalida
            binding.tvHoraLlegada.text = opcion.horaLlegada
            binding.durationText.text = opcion.duracion
            binding.priceText.text = opcion.precio
            binding.chooseButton.setOnClickListener { onItemClick(opcion) }
        }
    }
}
