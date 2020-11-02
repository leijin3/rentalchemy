package com.example.rentalchemy.database.model

import java.util.*

data class Appliance (var idi: Long, var propertyId: Long, var type: String, var price: Float,
                      var datePurchased: Date, var warranty: Date){
}