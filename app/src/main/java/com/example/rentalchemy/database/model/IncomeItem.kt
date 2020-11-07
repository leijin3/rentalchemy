package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class IncomeItem (var id: Long, var property_id: Long, var type: String,
                       var amt_received: Float, var date_received: String) {
}