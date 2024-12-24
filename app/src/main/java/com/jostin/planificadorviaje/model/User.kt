package com.jostin.planificadorviaje.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val profilePicture: String = ""
)