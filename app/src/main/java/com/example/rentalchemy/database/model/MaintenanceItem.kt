package com.example.rentalchemy.database.model

import java.util.*

data class MaintenanceItem (var id: Long, var propertyId: Long, var description: String,
                            var contractor: String, var dateFinished: Date){
}