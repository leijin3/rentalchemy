package com.example.rentalchemy.database.model

import java.util.*

data class Expense (var id: Long, var propertyId: Long, var type: String, var dateSpent: Date,
                    var amtSpent: Float, var receiptURI: String){
}