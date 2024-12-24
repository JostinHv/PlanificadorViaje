package com.jostin.planificadorviaje.model

data class AdminRestaurante(
    val id: String,
    val nombre: String,
    val precio: Double,
    val puntaje: Double,
    val imagen: String,
    val ciudad: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdminRestaurante) return false

        return id == other.id &&
                nombre == other.nombre &&
                precio == other.precio &&
                puntaje == other.puntaje &&
                imagen == other.imagen &&
                ciudad == other.ciudad
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + precio.hashCode()
        result = 31 * result + puntaje.hashCode()
        result = 31 * result + imagen.hashCode()
        result = 31 * result + ciudad.hashCode()
        return result
    }
}