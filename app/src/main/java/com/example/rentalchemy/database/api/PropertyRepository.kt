package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.Property
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyRepository(private val jsApi: JsonServerApi) {

    //functions to serve data to the ViewModel
    suspend fun getUserId(userName: String): Long? {
        val userList = jsApi.getUserList(userName)
        return if (userList.size == 1) {
            userList[0].id
        } else
            null
    }


    suspend fun getPropertyList(userId: Int): List<Property>? {
        return jsApi.getPropertyList(userId)
    }

    fun createProperty(propertyInfo: Property, onResult: (Property?) -> Unit) {
        jsApi.createProperty(propertyInfo).enqueue(
            object : Callback<Property> {
                override fun onFailure(call: Call<Property>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Property>, response: Response<Property>) {
                    val addProperty = response.body()
                    onResult(addProperty)


                }
            }
        )
    }


    fun deleteProperty(propertyId: Long, onResult: (Property?) -> Unit) {
        jsApi.deleteProperty(propertyId).enqueue(
            object : Callback<Property> {
                override fun onFailure(call: Call<Property>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Property>, response: Response<Property>) {
//                    val deleteProperty = response.body()
//                    onResult(deleteProperty)

//                    Log.d("XXX", deleteProperty.toString())
                }
            }
        )
    }

    fun updateProperty(propertyId: Long, newProperty: Property, onResult: (Property?) -> Unit) {
        jsApi.updateProperty(propertyId, newProperty).enqueue(
            object : Callback<Property> {
                override fun onFailure(call: Call<Property>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Property>, response: Response<Property>) {
                    onResult(newProperty)
                }
            }
        )
    }
}