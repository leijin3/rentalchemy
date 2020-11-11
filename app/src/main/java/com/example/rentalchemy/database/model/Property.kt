package com.example.rentalchemy.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property(
    var id: Long,
    @SerialName("user_id") var landlordID: Long,
    @SerialName("st_address") var streetAddress: String,
    var city: String,
    var state: String,
    var zip: String,
    var rent_amt: String,
    // https://github.com/Kotlin/kotlinx.serialization/issues/457
    @SerialName("type") var propertyType: String,
    var sqft: Int,
    var num_beds: Int,
    var num_baths: Int,
    var cost_basis: String,
    var date_acquired: String,
    var year_built: String,
    var parking: Int
)