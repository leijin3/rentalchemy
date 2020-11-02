package com.example.rentalchemy.database.model

import java.util.*

data class Tenant (var id: Long, var propertyId: Long, var name: String,
                    var email: String, var phone: String, var leaseStart: Date){
}