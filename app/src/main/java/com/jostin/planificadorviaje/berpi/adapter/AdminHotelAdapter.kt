package com.jostin.planificadorviaje.berpi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jostin.planificadorviaje.berpi.model.AdminHotel
import com.jostin.planificadorviaje.databinding.ItemAdminHotelBinding

class AdminHotelAdapter : ListAdapter<AdminHotel, HotelViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val binding = ItemAdminHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdminHotel>() {
            override fun areItemsTheSame(oldItem: AdminHotel, newItem: AdminHotel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: AdminHotel, newItem: AdminHotel) = oldItem == newItem
        }
    }
}

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