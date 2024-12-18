package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jostin.planificadorviaje.R
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
                starsRatingBar.rating = hotel.stars.toFloat()
                nombreHotelText.text = hotel.name
                precioPorNocheText.text = "Precio por noche S/${hotel.price_per_person.toInt()}"
                precioPorPersonaText.text = "Precio por persona S/${hotel.price_per_person.toInt()}"
                // Load image using Glide
                if (hotel.image_url != null) {
                    Glide.with(itemView.context)
                        .load(hotel.image_url)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(hotelImageView)
                } else {
                    // Use a colored placeholder if no image URL is available
                    hotelImageView.setImageDrawable(ColorDrawable(ContextCompat.getColor(itemView.context, R.color.placeholder_color)))
                }
                // Configurar el clic en el botón "Ver reserva"
                verReservaButton.setOnClickListener {
                    onReservaClick(hotel) // Llamar al callback definido en el adaptador
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