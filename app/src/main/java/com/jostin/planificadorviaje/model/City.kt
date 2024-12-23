package com.jostin.planificadorviaje.model

import java.io.Serializable

data class City(
    val id: String = "",
    val name: String = "",
    val department: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String?= ""
) : Serializable {
    constructor() : this("", "", "", 0.0, 0.0)
}
