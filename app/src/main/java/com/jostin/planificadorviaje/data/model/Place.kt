package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "places")
data class Place(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String? = null,
    val rating: Float? = 0f,
    val details: List<String> = emptyList(),
    val price: Double? = 0.0,
) : Serializable