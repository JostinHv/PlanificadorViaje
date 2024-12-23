package com.jostin.planificadorviaje.model

enum class TipoHabitacion(val nombre: String, val precioBase: Double) {
    SUITE("Suite", 200.0),
    DELUXE("Deluxe", 180.0),
    DOBLE("Doble", 150.0),
    EJECUTIVA("Ejecutiva", 220.0),
    FAMILIAR("Familiar", 250.0)
}
