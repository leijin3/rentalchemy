package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.MaintenanceItem

class MaintenanceRepository(private val jsApi: JsonServerApi) {
    suspend fun getMaintenanceList(property_id: Long): List<MaintenanceItem> {
        return jsApi.getMaintenanceList(property_id)
    }
}