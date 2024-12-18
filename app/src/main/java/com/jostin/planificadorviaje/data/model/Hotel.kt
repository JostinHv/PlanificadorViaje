package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "hotels")
data class Hotel(
    @PrimaryKey val id: String,
    val name: String,
    val stars: Double,
    val price_per_person: Double,
    val price_per_day: Double,
    val checkIn: String,
    val checkOut: String,
    @Embedded(prefix = "place_")
    val place: Place,
    val image_url: String? = null
)