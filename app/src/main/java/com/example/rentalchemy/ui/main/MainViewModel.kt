package com.example.rentalchemy.ui.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentalchemy.database.api.JsonServerApi
import com.example.rentalchemy.database.api.PropertyRepository
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.database.model.Tenant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
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

    private val dummyPropertyID = 999.toLong()
    private val dummyProperty = Property(
        id = dummyPropertyID,
        landlordID = 1,
        streetAddress = "123 Fifth Ave",
        city = "New York",
        state = "NY",
        zip = "10003",
        rent_amt = "5000",
        propertyType = "apartment",
        sqft = 3000,
        num_beds = 3,
        num_baths = 2,
        cost_basis = "2000000",
        date_acquired = "2020/01/01",
        year_built = "1999",
        parking = 2
    )


    fun createDummyProperty() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        propertyRepository.createProperty(dummyProperty) {
            if (it?.id != null) {
                // it = newly added property parsed as response
                // it?.id = newly added property ID
                fetchProperties()
                Log.d("XXX", "fetch new")
            } else {
                Log.d("XXX", "Error adding new property")
            }
        }

    }

    fun deleteDummyProperty() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        propertyRepository.deleteProperty(dummyPropertyID) {
            if (it?.id != null) {
                // it = newly added property parsed as response
                // it?.id = newly added property ID
                Log.d("XXX", "not deleted!")
            } else {
                Toast.makeText(getApplication(), "Property deleted!", Toast.LENGTH_SHORT).show()
                fetchProperties()
            }
        }
    }

    fun updateProperty(newProperty: Property) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        propertyRepository.updateProperty(selectedProperty!!.id, newProperty) {
            if (it?.id != null) {
                selectedProperty = newProperty
                Toast.makeText(getApplication(), "Property updated!", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("XXX", "Not updated")
            }
        }
    }

    fun getTenant() : Tenant{
        //Write Me
        // Should get tenant info for selected Property from database.  Below for testing only.
        return Tenant(1, 1, "Sally Sutherford", "ssseashells@seashore.com", "509.567.1234",
        "10/01/2020")
    }
    fun updateTenant(tenant: Tenant){
        //Write Me
    }

    fun addMaintenance(description: String, contractor: String, date : String){
        //Write Me  -- create new MaintenanceItem object, use selectedProperty, save to database
    }

    companion object {
        var selectedProperty: Property? = null
    }


}