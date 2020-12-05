package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class Appliance(
    var year: Int,
    var month: Int?,
    var id: Long = Long.MIN_VALUE,
    var property_id: Long,
    var type: String,
    var price: Float,
    var date_purchased: String,
    var warranty: String
) {
}