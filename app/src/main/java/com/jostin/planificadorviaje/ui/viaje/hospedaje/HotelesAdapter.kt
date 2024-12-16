package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jostin.planificadorviaje.data.model.Hotel
import com.jostin.planificadorviaje.databinding.ItemHotelBinding

// HotelesAdapter.kt
class HotelesAdapter(
    private val onReservaClick: (Hotel) -> Unit
) : ListAdapter<Hotel, HotelesAdapter.HotelViewHolder>(HotelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val binding = ItemHotelBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HotelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HotelViewHolder(
        private val binding: ItemHotelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: Hotel) {
            binding.apply {
                categoriaText.text = hotel.categoria
                nombreHotelText.text = hotel.nombre
                precioText.text = "Precio por noche S/${hotel.precioNoche.toInt()}"

                verReservaButton.setOnClickListener {
                    onReservaClick(hotel)
                }
            }
        }
    }

    private class HotelDiffCallback : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }
}