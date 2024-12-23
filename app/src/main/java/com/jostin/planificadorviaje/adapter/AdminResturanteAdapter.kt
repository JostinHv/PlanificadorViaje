package com.jostin.planificadorviaje.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jostin.planificadorviaje.model.AdminRestaurante
import com.jostin.planificadorviaje.databinding.ItemAdminResturanteBinding

class AdminRestauranteAdapter : ListAdapter<AdminRestaurante, AdminRestauranteAdapter.RestauranteViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val binding = ItemAdminResturanteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestauranteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdminRestaurante>() {
            override fun areItemsTheSame(oldItem: AdminRestaurante, newItem: AdminRestaurante) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: AdminRestaurante, newItem: AdminRestaurante) = oldItem == newItem
        }
    }


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


}