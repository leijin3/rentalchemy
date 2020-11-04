package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class Appliance (var id: Long, var property_id: Long, var type: String, var price: Float,
                      var date_purchased: String, var warranty: String){
}