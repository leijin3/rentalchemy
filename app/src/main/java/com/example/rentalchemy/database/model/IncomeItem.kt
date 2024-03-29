package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class IncomeItem(
    var year: Int,
    var month: Int,
    var id: Long = Long.MIN_VALUE,
    var property_id: Long,
    var type: String,
    var amt_received: Float,
    var date_received: String
)