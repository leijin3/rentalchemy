package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.IncomeItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomeRepository(private val jsApi: JsonServerApi) {
    suspend fun getIncomeList(propertyId: Long): List<IncomeItem> {
        return jsApi.getIncomeList(propertyId)
    }

    fun create(incomeItemInfo: IncomeItem, onResult: (IncomeItem?) -> Unit) {
        jsApi.createMaintenanceItem(incomeItemInfo).enqueue(
            object : Callback<IncomeItem> {
                override fun onFailure(call: Call<IncomeItem>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<IncomeItem>, response: Response<IncomeItem>) {
                    val incomeItemInfo = response.body()
                    onResult(incomeItemInfo)
                }
            }
        )
    }


    fun delete(id: Long, onResult: (IncomeItem?) -> Unit) {
        jsApi.deleteIncomeItem(id).enqueue(
            object : Callback<IncomeItem> {
                override fun onFailure(call: Call<IncomeItem>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<IncomeItem>,
                    response: Response<IncomeItem>
                ) {
                }
            }
        )
    }

}