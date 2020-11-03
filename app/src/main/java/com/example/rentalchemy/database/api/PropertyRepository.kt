package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.Property

class PropertyRepository (private val jsApi: JsonServerApi){

    //functions to serve data to the ViewModel

    suspend fun getPropertyList(userId: Int): List<Property>? {
        return  jsApi.getPropertyList(userId)
    }
}