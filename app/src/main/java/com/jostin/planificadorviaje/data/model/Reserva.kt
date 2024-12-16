package com.jostin.planificadorviaje.data.model

data class Reserva(
    val id: String,
    val hotel: Hotel,
    val fechaEntrada: String,
    val fechaSalida: String,
    val personas: Int,
    val tipoHabitacion: String,
    val precioTotal: Double
)