package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class Tenant (var id: Long, var property_id: Long, var name: String,
                    var email: String, var phone: String, var leaseStart: String){
}