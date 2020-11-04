package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User (var id: Long, var user_Name: String, var created_at: String) {
}