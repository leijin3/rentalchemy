package com.example.rentalchemy.ui.main

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentalchemy.database.api.*
import com.example.rentalchemy.database.model.Expense
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.database.model.Tenant
import com.example.rentalchemy.database.model.MaintenanceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.measureTime

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fetchedProperties = MutableLiveData<List<Property>>()
    private val fetchedMaintenanceItems = MutableLiveData<List<MaintenanceItem>>()
    private var userId = MutableLiveData<Int>().apply { value = 1 }
    private var currentPhoto = MutableLiveData<Uri>()

    private val jsApi = JsonServerApi.create()
    private val propertyRepository = PropertyRepository(jsApi)
    private val expenseRepository = ExpenseRepository(jsApi)
    private val maintenanceRepository = MaintenanceRepository(jsApi)
    private val reportGenerator = ReportGenerator()


    fun getUserId(userName: String) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        userId.postValue(propertyRepository.getUserId(userName)?.toInt())
    }

    fun fetchProperties() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedProperties.postValue(propertyRepository.getPropertyList(userId.value!!))
    }

    fun fetchMaintenanceItems(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedMaintenanceItems.postValue(maintenanceRepository.getMaintenanceList(propertyId))
    }


    fun observeProperties(): LiveData<List<Property>> {
        return fetchedProperties
    }

    fun observeMaintenanceItems(): LiveData<List<MaintenanceItem>> {
        return fetchedMaintenanceItems
    }

    private val dummyPropertyID = 999.toLong()
    private val dummyProperty = Property(
        year = 2001,
        month = 1,
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


    fun updateCurrentPhoto(newPhotoName: Uri) {
        currentPhoto.value = newPhotoName
    }

    fun clearCurrentPhoto() {
        currentPhoto.value = null
    }

    fun observeCurrentPhoto(): LiveData<Uri> {
        return currentPhoto
    }

    fun getTenant(): Tenant {
        //Write Me
        // Should get tenant info for selected Property from database.  Below for testing only.
        return Tenant(
            1, 1, "Sally Sutherford", "ssseashells@seashore.com", "509.567.1234",
            "10/01/2020"
        )
    }

    fun updateTenant(tenant: Tenant) {
        //Write Me
    }

    fun addMaintenance(year: Int, month: Int, description: String, contractor: String, date: String) {
        //Write Me  -- create new MaintenanceItem object, use selectedProperty, save to database
    }

    fun addIncome(year: Int, month: Int, type: String, amount: Float, date: String) {
        //Write Me -- similar to above
    }

    fun addExpense(
        year: Int,
        month: Int,
        propertyId: Long,
        type: String,
        amount: Float,
        date: String,
        receiptURL: String
    ) =
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {

            val newExpense = Expense(
                year = year,
                month = month,
                property_id = propertyId,
                type = type,
                amount_spent = amount,
                date_spent = date,
                receipt_url = receiptURL
            )

            expenseRepository.add(newExpense) {
//                if (it?.id != null) {
//                    // it = newly added property parsed as response
//                    // it?.id = newly added property ID
////                    fetchProperties()
//                    Log.d("XXX", "fetch new")
//                } else {
//                    Log.d("XXX", "Error adding new property")
//                }
            }

        }

    fun addAppliance(year: Int, month: String, type: String, price: Float, date: String, warranty: String) {
        //Write Me -- similar to above
    }

    fun generateExpenseReport(appContext: Context) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            userId.value?.let { reportGenerator.generateReport(appContext, it) }
        }
    }

    companion object {
        var landlordID: Long? = null
        var selectedProperty: Property? = null
        var selectedExpense: Expense? = null
    }


}