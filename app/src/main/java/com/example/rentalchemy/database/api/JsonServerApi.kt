package com.example.rentalchemy.database.api

import android.text.SpannableString
import android.util.Log
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.database.model.User
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.serialization.*
import kotlinx.serialization.json.*


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
            .host("localhost")
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