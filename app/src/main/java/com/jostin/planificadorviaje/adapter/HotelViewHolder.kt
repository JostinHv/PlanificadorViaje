package com.jostin.planificadorviaje.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jostin.planificadorviaje.databinding.ItemAdminHotelBinding
import com.jostin.planificadorviaje.model.AdminHotel

class HotelViewHolder(private val binding: ItemAdminHotelBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(hotel: AdminHotel) {
        binding.apply {
            hotelName.text = hotel.nombre
            hotelPrice.text = "S/${hotel.precio}"
            reviewCount.text = "${hotel.puntaje} reseña"
            ratingBar.rating = hotel.puntaje.toFloat()
            Glide.with(hotelImage.context).load(hotel.imagen).into(hotelImage)
        }
    }
}