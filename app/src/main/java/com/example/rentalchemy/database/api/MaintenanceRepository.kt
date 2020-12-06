package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.MaintenanceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaintenanceRepository(private val jsApi: JsonServerApi) {

    fun create(maintenanceItemInfo: MaintenanceItem, onResult: (MaintenanceItem?) -> Unit) {
        jsApi.createMaintenanceItem(maintenanceItemInfo).enqueue(
            object : Callback<MaintenanceItem> {
                override fun onFailure(call: Call<MaintenanceItem>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<MaintenanceItem>,
                    response: Response<MaintenanceItem>
                ) {
                    val newMaintenanceItem = response.body()
                    onResult(newMaintenanceItem)
                }
            }
        )
    }

    fun update(
        id: Long,
        newMaintenanceItem: MaintenanceItem,
        onResult: (MaintenanceItem?) -> Unit
    ) {
        jsApi.updateMaintenanceItem(id, newMaintenanceItem).enqueue(
            object : Callback<MaintenanceItem> {
                override fun onFailure(call: Call<MaintenanceItem>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<MaintenanceItem>,
                    response: Response<MaintenanceItem>
                ) {
                    onResult(newMaintenanceItem)
                }
            }
        )
    }

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
