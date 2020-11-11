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
    private val fetchedProperties = MutableLiveData<List<Property>>()
    private var userId = MutableLiveData<Int>().apply { value = 1 }

    private val jsApi = JsonServerApi.create()
    private val propertyRepository = PropertyRepository(jsApi)


    fun fetchProperties() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedProperties.postValue(propertyRepository.getPropertyList(userId.value!!))
    }


    fun observeProperties(): LiveData<List<Property>> {
        return fetchedProperties
    }

    companion object {
        private var selectedProperty: Property? = null

        fun getProperty(): Property? {
            return selectedProperty
        }

        fun setProperty(property: Property) {
            selectedProperty = property
        }
    }


}