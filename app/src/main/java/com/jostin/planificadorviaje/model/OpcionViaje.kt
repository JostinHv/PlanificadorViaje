package com.jostin.planificadorviaje.model

data class OpcionViaje(
    val id: String,
    val horaSalida: String,
    val horaLlegada: String,
    val duracion: String,
    val precio: String
)