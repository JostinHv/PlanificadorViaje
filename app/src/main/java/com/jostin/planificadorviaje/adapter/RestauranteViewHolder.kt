package com.jostin.planificadorviaje.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jostin.planificadorviaje.databinding.ItemAdminResturanteBinding
import com.jostin.planificadorviaje.model.AdminRestaurante

class RestauranteViewHolder(private val binding: ItemAdminResturanteBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(restaurante: AdminRestaurante) {
        binding.apply {
            restauranteName.text = restaurante.nombre
            restaurantePrice.text = "S/${restaurante.precio}"
            reviewCount.text = "${restaurante.puntaje} reseñas"
            ratingBar.rating = restaurante.puntaje.toFloat()
            Glide.with(restauranteImage.context).load(restaurante.imagen).into(restauranteImage)
        }
    }
}