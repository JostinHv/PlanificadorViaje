package com.jostin.planificadorviaje.model

data class AdminHotel(
    val id: String = "",
    val nombre: String = "",
    val ciudad: String = "",
    val precio: Double = 0.0,
    val imagen: String = "",
    val puntaje: Double = 0.0
)