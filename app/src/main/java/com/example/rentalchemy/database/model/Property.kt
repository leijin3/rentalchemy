package com.example.rentalchemy.database.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Property(
    var id: Long,
    @SerializedName("user_id")
    var landlordID: Long,
    @SerializedName("st_address")
    var streetAddress: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("state")
    var state: String,
    @SerializedName("zip")
    var zip: String,
    @SerializedName("rent_amt")
    var rent_amt: String,
    @SerializedName("type")
    var propertyType: String,
    @SerializedName("sqft")
    var sqft: Int,
    @SerializedName("num_beds")
    var num_beds: Int,
    @SerializedName("num_baths")
    var num_baths: Int,
    @SerializedName("cost_basis")
    var cost_basis: String,
    @SerializedName("date_acquired")
    var date_acquired: String,
    @SerializedName("year_built")
    var year_built: String,
    @SerializedName("parking")
    var parking: Int
)  {
}