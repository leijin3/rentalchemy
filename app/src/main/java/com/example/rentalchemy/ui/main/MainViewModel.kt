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
import com.example.rentalchemy.database.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fetchedProperties = MutableLiveData<List<Property>>()
    private val fetchedExpenses = MutableLiveData<List<Expense>>()
    private val fetchedIncomeItems = MutableLiveData<List<IncomeItem>>()
    private val fetchedMaintenanceItems = MutableLiveData<List<MaintenanceItem>>()
    private val fetchedAppliances = MutableLiveData<List<Appliance>>()

    private var userId = MutableLiveData<Int>().apply { value = 1 }
    private var currentPhoto = MutableLiveData<Uri>()

    private val jsApi = JsonServerApi.create()
    private val propertyRepository = PropertyRepository(jsApi)
    private val expenseRepository = ExpenseRepository(jsApi)
    private val incomeRepository = IncomeRepository(jsApi)
    private val maintenanceRepository = MaintenanceRepository(jsApi)
    private val applianceRepository = ApplianceRepository(jsApi)
    private val reportGenerator = ReportGenerator()


    fun getUserId(userName: String) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        userId.postValue(propertyRepository.getUserId(userName)?.toInt())
        landlordID = userId.value!!.toLong()
    }

    fun fetchAppliances(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedAppliances.postValue(applianceRepository.getApplianceList(propertyId))
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

    fun fetchExpenses(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedExpenses.postValue(expenseRepository.getExpenseList(propertyId))
    }

    fun fetchIncomes(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedIncomeItems.postValue(incomeRepository.getIncomeList(propertyId))
    }

    fun observeAppliances(): LiveData<List<Appliance>> {
        return fetchedAppliances
    }

    fun observeIncomes(): LiveData<List<IncomeItem>> {
        return fetchedIncomeItems
    }
    fun observeProperties(): LiveData<List<Property>> {
        return fetchedProperties
    }

    fun observeMaintenanceItems(): LiveData<List<MaintenanceItem>> {
        return fetchedMaintenanceItems
    }


    fun observeExpenses(): LiveData<List<Expense>> {
        return fetchedExpenses
    }

    fun createProperty(
        year: Int,
        month: Int,
        landlordID: Long,
        streetAddress: String,
        city: String,
        state: String,
        zip: String,
        rent_amt: String,
        propertyType: String,
        sqft: Int,
        num_beds: Int,
        num_baths: Int,
        cost_basis: String,
        date_acquired: String,
        year_built: String,
        parking: Int

    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        val newProperty = Property(
            year = year,
            month = month,
            landlordID = landlordID,
            streetAddress = streetAddress,
            city = city,
            state = state,
            zip = zip,
            rent_amt = rent_amt,
            propertyType = propertyType,
            sqft = sqft,
            num_beds = num_beds,
            num_baths = num_baths,
            cost_basis = cost_basis,
            date_acquired = date_acquired,
            year_built = year_built,
            parking = parking
        )
        propertyRepository.createProperty(newProperty) {
            if (it?.id != null) {
                fetchProperties()
                Log.d("XXX", "fetch new")
            } else {
                Log.d("XXX", "Error adding new property")
            }
        }
    }


    fun deleteProperty(propertyID: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        propertyRepository.deleteProperty(propertyID) {
            if (it?.id != null) {

                Log.d("XXX", "not deleted!")
            } else {
                Toast.makeText(getApplication(), "Property deleted!", Toast.LENGTH_SHORT).show()
                fetchProperties()
            }
        }
    }

    fun deleteMaintenanceItem(id: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        maintenanceRepository.delete(id) {
            if (it?.id != null) {

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

    fun addMaintenance(
        year: Int,
        month: Int,
        description: String,
        contractor: String,
        date: String
    ) {
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

    fun addAppliance(
        year: Int,
        month: String,
        type: String,
        price: Float,
        date: String,
        warranty: String
    ) {
        //Write Me -- similar to above
    }

    fun generateExpenseReport(appContext: Context, year: Int) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            year.let { reportGenerator.generateExpenseReport(appContext, selectedProperty?.id.toString(), it) }
        }
    }

    fun generateIncomeReport(appContext: Context, year: Int) {
        viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO
        ) {
            year.let { reportGenerator.generateIncomeReport(appContext, selectedProperty?.id.toString(), it) }
        }
    }

    fun generateMaintReport(appContext: Context, year: Int) {
        viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO
        ) {
            year.let { reportGenerator.generateYearlyMaintenanceReport(appContext, selectedProperty?.id.toString(), it) }
        }
    }

    fun generateMaintHistory(appContext: Context) {
        viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO
        ) {
            reportGenerator.generateMaintenanceHistoryReport(appContext, selectedProperty?.id.toString())
        }
    }


    companion object {
        var landlordID: Long? = null
        var selectedProperty: Property? = null
        var selectedExpense: Expense? = null
        var selectedMaintenanceItem: MaintenanceItem? = null
    }


}