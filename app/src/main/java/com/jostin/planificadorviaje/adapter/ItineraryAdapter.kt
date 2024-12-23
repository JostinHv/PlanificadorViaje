package com.jostin.planificadorviaje.adapter
// ItineraryAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.databinding.ItemItineraryBinding
import java.text.SimpleDateFormat
import java.util.*


class ItineraryAdapter(private val onItemClick: (Itinerary) -> Unit) :
    ListAdapter<Itinerary, ItineraryAdapter.ItineraryViewHolder>(ItineraryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val binding =
            ItemItineraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItineraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItineraryViewHolder(private val binding: ItemItineraryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itinerary: Itinerary) {
            binding.apply {
                Glide.with(root)
                    .load(R.drawable.item_itinerary_bg)
                    .centerCrop()
                    .into(itineraryImage)
                itineraryName.text = itinerary.name
                destinationChip.text = itinerary.destination
                itineraryDates.text = formatDateRange(itinerary.startDate, itinerary.endDate)
                root.setOnClickListener { onItemClick(itinerary) }
            }
        }

        private fun formatDateRange(startDate: Date, endDate: Date): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        }
    }

    class ItineraryDiffCallback : DiffUtil.ItemCallback<Itinerary>() {
        override fun areItemsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
            return oldItem == newItem
        }
    }
}