package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable


@Serializable
data class Expense (var id: Long, var property_id: Long, var type: String, var date_spent: String,
                    var amount_spent: Float, var receipt_url: String){
}