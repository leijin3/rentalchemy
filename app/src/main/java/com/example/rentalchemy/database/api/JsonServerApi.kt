package com.example.rentalchemy.database.api

import android.util.Log
import com.example.rentalchemy.database.model.Expense
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.database.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*


interface JsonServerApi {

    @GET(
        "users"
    )
    suspend fun getUserList(@Query("user_name") username: String): List<User>


    // Properties
    @GET(
        "properties"
    )
    suspend fun getPropertyList(@Query("user_id") userId: Int): List<Property>


    @Headers("Content-Type: application/json")
    @POST("properties")
    fun createProperty(@Body propertyInfo: Property): Call<Property>


    @DELETE("properties/{id}")
    fun deleteProperty(@Path("id") propertyId: Long): Call<Property>

    @PUT("properties/{id}")
    fun updateProperty(@Path("id") propertyId: Long, @Body newProperty: Property): Call<Property>


    // Expenses
    @GET(
        "expenses"
    )
    suspend fun getExpenseList(@Query("property_id") propertyId: Long): List<Expense>

    @Headers("Content-Type: application/json")
    @POST("expenses")
    fun createExpense(@Body expenseInfo: Expense): Call<Expense>

    @DELETE("expenses/{id}")
    fun deleteExpense(@Path("id") id: Long): Call<Expense>

    @PUT("expenses/{id}")
    fun updateExpense(@Path("id") id: Long, @Body newExpense: Expense): Call<Expense>


    companion object {

        private var httpurl = HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(3000)
            .build()
        private val contentType = "application/json".toMediaType()

        fun create(): JsonServerApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): JsonServerApi {
            Log.d(
                "XXX",
                "create: httpurl = " + httpurl.toString() + " contentType = " + contentType.type
            )
            // https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(JsonServerApi::class.java)
        }
    }


}