package com.jostin.planificadorviaje.data.model

data class OpcionViaje(
    val id: String,
    val horaSalida: String,
    val horaLlegada: String,
    val duracion: String,
    val precio: String
)