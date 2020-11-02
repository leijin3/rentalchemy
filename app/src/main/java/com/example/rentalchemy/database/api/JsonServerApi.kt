package com.example.rentalchemy.database.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonServerApi {

    @GET ("/users?user_name={user_name}"
    )
    suspend fun getUserID(@Path("user_name") username: String) : UserResponse

    @GET ("/properties?user_id={userId}"
    )
    suspend fun getPropertyList(@Path("userId") userId: Int) : PropertyListResponse


}