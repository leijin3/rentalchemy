package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.IncomeItem

class IncomeRepository(private val jsApi: JsonServerApi) {
    suspend fun getIncomeList(propertyId: Long): List<IncomeItem> {
        return jsApi.getIncomeList(propertyId)
    }

}