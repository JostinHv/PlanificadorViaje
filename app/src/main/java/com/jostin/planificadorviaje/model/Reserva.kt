package com.jostin.planificadorviaje.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "reserva")
data class Reserva(
    @PrimaryKey val id: String,
    @Embedded(prefix = "hotel_") var hotel: Hotel,
    @Embedded(prefix = "user_") var user: User?,
    var discount_percentage: Int = 0, // Porcentaje de descuento (opcional)
    var fechaEntrada: String = "",
    var fechaSalida: String = "",
    var personas: Int = 0,
    var tipoHabitacion: String = TipoHabitacion.SUITE.nombre,
    var precioTotal: Double = 0.0
)
