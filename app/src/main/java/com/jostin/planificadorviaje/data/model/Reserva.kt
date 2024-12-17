package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "reserva")
data class Reserva(
    @PrimaryKey val id: String,
    @Embedded(prefix = "hotel_") val hotel: Hotel,
    @Embedded(prefix = "user_") val user: User?,
    val discount_percentage: Int = 0, // Porcentaje de descuento (opcional)
    val fechaEntrada: String = "",
    val fechaSalida: String = "",
    val personas: Int = 0,
    val tipoHabitacion: String = TipoHabitacion.SUITE.nombre,
    var precioTotal: Double = 0.0
)
