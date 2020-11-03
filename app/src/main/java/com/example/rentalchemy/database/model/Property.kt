package com.example.rentalchemy.database.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Year
import java.util.*

@Serializable
data class Property (var id: Long,
                     var user_id: Long,
                     var st_address: String,
                     var city: String,
                     var state: String,
                     var zip : String,
                     var rent_amt: String,
                     var type: String,
                     var sqft: Int,
                     var num_beds: Int,
                     var num_baths: Int,
                     var cost_basis: String,
                     var date_acquired: String,
                     var year_built: String,
                     var parking: Int)  {
}