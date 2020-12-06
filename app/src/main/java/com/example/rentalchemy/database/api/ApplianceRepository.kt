package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.Appliance
import com.example.rentalchemy.database.model.MaintenanceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplianceRepository(private val jsApi: JsonServerApi) {

    fun create(applianceInfo: Appliance, onResult: (Appliance?) -> Unit) {
        jsApi.createAppliance(applianceInfo).enqueue(
            object : Callback<Appliance> {
                override fun onFailure(call: Call<Appliance>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Appliance>, response: Response<Appliance>) {
                    val newAppliance = response.body()
                    onResult(newAppliance)
                }
            }
        )
    }

    suspend fun getApplianceList(propertyId: Long): List<Appliance> {
        return jsApi.getApplianceList(propertyId)
    }

    fun update(
        id: Long,
        newAppliance: Appliance,
        onResult: (Appliance?) -> Unit
    ) {
        jsApi.updateAppliance(id, newAppliance).enqueue(
            object : Callback<Appliance> {
                override fun onFailure(call: Call<Appliance>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Appliance>,
                    response: Response<Appliance>
                ) {
                    onResult(newAppliance)
                }
            }
        )
    }

    fun delete(id: Long, onResult: (MaintenanceItem?) -> Unit) {
        jsApi.deleteAppliance(id).enqueue(
            object : Callback<Appliance> {
                override fun onFailure(call: Call<Appliance>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Appliance>,
                    response: Response<Appliance>
                ) {
                }
            }
        )
    }

}