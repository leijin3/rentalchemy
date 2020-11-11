package com.example.rentalchemy.database.api

import android.util.Log
import com.example.rentalchemy.database.model.Property
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyRepository(private val jsApi: JsonServerApi) {

    //functions to serve data to the ViewModel

    suspend fun getPropertyList(userId: Int): List<Property>? {
        return jsApi.getPropertyList(userId)
    }

    fun createProperty(propertyInfo: Property, onResult: (Property?) -> Unit) {
        jsApi.createProperty(propertyInfo).enqueue(
            object : Callback<Property> {
                override fun onFailure(call: Call<Property>, t: Throwable) {
                    Log.d("XXX", "d failed")
                    onResult(null)
                }

                override fun onResponse(call: Call<Property>, response: Response<Property>) {
                    val addProperty = response.body()
                    Log.d("XXX", addProperty.toString())
                    onResult(addProperty)


                }
            }
        )
    }


    fun deleteProperty(propertyId: Long, onResult: (Property?) -> Unit) {
        jsApi.deleteProperty(propertyId).enqueue(
            object : Callback<Property> {
                override fun onFailure(call: Call<Property>, t: Throwable) {
                    Log.d("XXX", "d failed")
                    onResult(null)
                }

                override fun onResponse(call: Call<Property>, response: Response<Property>) {
                    val deleteProperty = response.body()
//                    onResult(deleteProperty)

                    Log.d("XXX", deleteProperty.toString())
                }
            }
        )
    }
}