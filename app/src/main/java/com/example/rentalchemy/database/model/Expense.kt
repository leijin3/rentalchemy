package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable


@Serializable
data class Expense(
    var year: Int, var month: Int, var id: Long = Long.MIN_VALUE, var property_id: Long, var type: String, var date_spent: String,
    var amount_spent: Float, var receipt_url: String
)