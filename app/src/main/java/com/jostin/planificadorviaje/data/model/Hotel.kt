package com.jostin.planificadorviaje.data.model

data class Hotel(
    val id: String,
    val nombre: String,
    val categoria: String,
    val precioNoche: Double,
    val imagen: String? = null
)