package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.Appliance

class ApplianceRepository(private val jsApi: JsonServerApi) {
    suspend fun getApplianceList(propertyId: Long): List<Appliance> {
        return jsApi.getApplianceList(propertyId)
    }

}