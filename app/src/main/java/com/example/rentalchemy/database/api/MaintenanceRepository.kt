package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.MaintenanceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaintenanceRepository(private val jsApi: JsonServerApi) {
    suspend fun getMaintenanceList(property_id: Long): List<MaintenanceItem> {
        return jsApi.getMaintenanceList(property_id)
    }

    fun delete(id: Long, onResult: (MaintenanceItem?) -> Unit) {
        jsApi.deleteMaintenanceItem(id).enqueue(
            object : Callback<MaintenanceItem> {
                override fun onFailure(call: Call<MaintenanceItem>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<MaintenanceItem>,
                    response: Response<MaintenanceItem>
                ) {
                }
            }
        )
    }
}
