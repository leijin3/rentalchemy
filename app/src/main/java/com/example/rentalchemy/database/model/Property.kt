package com.example.rentalchemy.database.model

import java.time.Year
import java.util.*

data class Property (var id: Long,
                        var userId: Long,
                        var streetAddress: String,
                        var city: String,
                        var state: String,
                        var zip : String,
                        var rent_amt: Float,
                        var type: String,
                        var sqft: Int,
                        var num_beds: Int,
                        var num_baths: Int,
                        var costBasis: Float,
                        var dateAcquired: Date,
                        var yearBuilt: Int,
                        var parking: Int)  {
}