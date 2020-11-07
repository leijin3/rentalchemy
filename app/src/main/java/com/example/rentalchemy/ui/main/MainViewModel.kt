package com.example.rentalchemy.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalchemy.database.api.JsonServerApi
import com.example.rentalchemy.database.api.PropertyRepository
import com.example.rentalchemy.database.model.Property
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val netProperties = MutableLiveData<List<Property>>()
    private var userId = MutableLiveData<Int> ().apply { value = 1 }

    private val jsApi = JsonServerApi.create()
    private val propertyRepository = PropertyRepository(jsApi)



    fun netProperties() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        netProperties.postValue(propertyRepository.getPropertyList(userId.value!!))
    }


    fun observeProperties(): LiveData<List<Property>> {
        return netProperties
    }

    fun getProperties(): List<Property>? {
        return netProperties.value
    }

}