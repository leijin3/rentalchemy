package com.example.rentalchemy.database.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property (var id: Long,
                     var user_id: Long,
                     @SerialName("st_address") var streetAddress: String,
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