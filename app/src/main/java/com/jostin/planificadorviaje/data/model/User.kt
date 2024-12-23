package com.jostin.planificadorviaje.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val profilePicture: String = ""
)