package com.example.rentalchemy.database.model

import java.util.*

data class IncomeItem (var id: Long, var propertyId: Long, var type: String,
                       var amtReceived: Float, var dateReceived: Date) {
}