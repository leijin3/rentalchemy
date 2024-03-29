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

    fun getTenant(): Tenant {
        //TODO: Should get tenant info for selected Property from database.  Below for testing only.
        return Tenant(
            1, 1, "Sally Sutherford", "ssseashells@seashore.com", "509.567.1234",
            "10/01/2020"
        )
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

    // Property
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

    fun fetchProperties() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        fetchedProperties.postValue(propertyRepository.getPropertyList(userId.value!!))
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

    // Maintenance
    fun createMaintenanceItem(
        year: Int,
        month: Int,
        description: String,
        contractor: String,
        date_finished: String
    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        val newMaintenanceItem = MaintenanceItem(
            year = year,
            month = month,
            property_id = selectedProperty!!.id,
            description = description,
            contractor = contractor,
            date_finished = date_finished,
        )
        maintenanceRepository.create(newMaintenanceItem) {
            if (it?.id != null) {
                fetchMaintenanceItems(selectedProperty!!.id)
                Log.d("XXX", "fetch new")
            } else {
                Log.d("XXX", "Error adding new property")
            }
        }
    }

    fun fetchMaintenanceItems(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        fetchedMaintenanceItems.postValue(maintenanceRepository.getMaintenanceList(propertyId))
    }

    fun updateMaintenanceItem(newMaintenanceItem: MaintenanceItem) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        maintenanceRepository.update(selectedMaintenanceItem!!.id, newMaintenanceItem) {
            if (it?.id != null) {
                selectedMaintenanceItem = newMaintenanceItem
                Toast.makeText(getApplication(), "Maintenance Item updated!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("XXX", "Not updated")
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
                Toast.makeText(getApplication(), "Maintenance Item deleted!", Toast.LENGTH_SHORT)
                    .show()
                fetchMaintenanceItems(selectedProperty!!.id)
                selectedMaintenanceItem = null
            }
        }
    }

    // Appliance
    fun createAppliance(
        year: Int,
        month: Int,
        type: String,
        price: Float,
        date_purchased: String,
        warranty: String
    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        val newAppliance = Appliance(
            year = year,
            month = month,
            property_id = selectedProperty!!.id,
            type = type,
            price = price,
            date_purchased = date_purchased,
            warranty = warranty

        )
        applianceRepository.create(newAppliance) {
            if (it?.id != null) {
                fetchAppliances(selectedProperty!!.id)
            } else {
                Log.d("XXX", "Error adding new appliance")
            }
        }
    }

    fun fetchAppliances(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        // Update LiveData from IO dispatcher, use postValue
        fetchedAppliances.postValue(applianceRepository.getApplianceList(propertyId))
    }

    fun updateAppliances(newAppliance: Appliance) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        applianceRepository.update(selectedAppliance!!.id, newAppliance) {
            if (it?.id != null) {
                selectedAppliance = newAppliance
                Toast.makeText(getApplication(), "Appliance updated!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("XXX", "Appliance Not updated")
            }
        }
    }

    fun deleteAppliance(id: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        applianceRepository.delete(id) {
            if (it?.id != null) {

                Log.d("XXX", "not deleted!")
            } else {
                Toast.makeText(getApplication(), "Appliance deleted!", Toast.LENGTH_SHORT).show()
                fetchAppliances(selectedProperty!!.id)
                selectedAppliance = null
            }
        }
    }


    // Income
    fun createIncomeItem(
        year: Int,
        month: Int,
        type: String,
        amt_received: Float,
        date_received: String,
    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        val newIncomeItem = IncomeItem(
            year = year,
            month = month,
            property_id = selectedProperty!!.id,
            type = type,
            amt_received = amt_received,
            date_received = date_received,
        )
        incomeRepository.create(newIncomeItem) {
            if (it?.id != null) {
                fetchIncomes(selectedProperty!!.id)
            } else {
                Log.d("XXX", "Error adding new income")
            }
        }
    }

    fun fetchIncomes(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        fetchedIncomeItems.postValue(incomeRepository.getIncomeList(propertyId))
    }

    fun updateIncomeItem(newIncomeItem: IncomeItem) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        incomeRepository.update(selectedIncomeItem!!.id, newIncomeItem) {
            if (it?.id != null) {
                selectedIncomeItem = newIncomeItem
                Toast.makeText(getApplication(), "Income item updated!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("XXX", "Income Not updated")
            }
        }
    }

    fun deleteIncomeItem(id: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        incomeRepository.delete(id) {
            if (it?.id != null) {

                Log.d("XXX", "not deleted!")
            } else {
                Toast.makeText(getApplication(), "Income Item deleted!", Toast.LENGTH_SHORT).show()
                fetchIncomes(selectedProperty!!.id)
                selectedIncomeItem  = null
            }
        }
    }


    // Expense
    fun createExpense(
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

            expenseRepository.create(newExpense) {
                if (it?.id != null) {
                    fetchExpenses(selectedProperty!!.id)
                } else {
                    Log.d("XXX", "Error adding new expense")
                }
            }
        }

    fun fetchExpenses(propertyId: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        fetchedExpenses.postValue(expenseRepository.getExpenseList(propertyId))
    }

    fun updateExpense(newExpense: Expense) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        expenseRepository.update(selectedExpense!!.id, newExpense) {
            if (it?.id != null) {
                selectedExpense = newExpense
                Toast.makeText(getApplication(), "Expense updated!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("XXX", "Expense Not updated")
            }
        }
    }

    fun deleteExpense(id: Long) = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {

        expenseRepository.delete(id) {
            if (it?.id != null) {

                Log.d("XXX", "not deleted!")
            } else {
                Toast.makeText(getApplication(), "Expense deleted!", Toast.LENGTH_SHORT).show()
                fetchExpenses(selectedProperty!!.id)
                selectedExpense = null
            }
        }
    }


    // Tenant

    // TODO: createTenant(propertyId: Long)

    // TODO: fetchTenants(propertyId: Long)

    // TODO: updateTenant(id: Long)

    // TODO: deleteTenant(id: Long)

    fun updateCurrentPhoto(newPhotoName: Uri) {
        currentPhoto.value = newPhotoName
    }

    fun clearCurrentPhoto() {
        currentPhoto.value = null
    }

    fun observeCurrentPhoto(): LiveData<Uri> {
        return currentPhoto
    }

    fun generateExpenseReport(appContext: Context, year: Int) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            year.let {
                reportGenerator.generateExpenseReport(
                    appContext,
                    selectedProperty?.id.toString(),
                    it
                )
            }
        }
    }

    fun generateIncomeReport(appContext: Context, year: Int) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            year.let {
                reportGenerator.generateIncomeReport(
                    appContext,
                    selectedProperty?.id.toString(),
                    it
                )
            }
        }
    }

    fun generateMaintReport(appContext: Context, year: Int) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            year.let {
                reportGenerator.generateYearlyMaintenanceReport(
                    appContext,
                    selectedProperty?.id.toString(),
                    it
                )
            }
        }
    }

    fun generateMaintHistory(appContext: Context) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            reportGenerator.generateMaintenanceHistoryReport(
                appContext,
                selectedProperty?.id.toString()
            )
        }
    }


    companion object {
        var landlordID: Long? = null
        var selectedProperty: Property? = null
        var selectedExpense: Expense? = null
        var selectedMaintenanceItem: MaintenanceItem? = null
        var selectedAppliance: Appliance? = null
        var selectedIncomeItem: IncomeItem? = null
    }


}