package com.example.rentalchemy.database.model

import kotlinx.serialization.Serializable

@Serializable
data class MaintenanceItem (var year: Int, var month: Int, var id: Long, var property_id: Long,
                            var description: String, var contractor: String,
                            var date_finished: String){
}