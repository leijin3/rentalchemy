package com.example.rentalchemy.database.api

import android.util.Log
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.database.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface JsonServerApi {

    @GET(
        "/users"
    )
    suspend fun getUserID(@Query("user_name") username: String): User

    @GET(
        "/properties"
    )
    suspend fun getPropertyList(@Query("user_id") userId: Int): List<Property>



    companion object {

        private var httpurl = HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(3000)
            .build()
        private val contentType = "application/json".toMediaType()

        fun create(): JsonServerApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): JsonServerApi {
            Log.d("XXX", "create: httpurl = " + httpurl.toString() + " contentType = " + contentType.type)
            // https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(JsonServerApi::class.java)
        }
    }


}