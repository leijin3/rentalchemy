package com.example.rentalchemy.database.api

import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rentalchemy.MaintenanceHistoryQuery
import com.example.rentalchemy.YearlyExpenseQuery
import com.example.rentalchemy.YearlyIncomeQuery
import com.example.rentalchemy.YearlyMaintenanceQuery
import java.io.FileWriter

class ReportGenerator {

    suspend fun generateExpenseReport(context: Context, user_id: Int){
        val response = try {
            apolloClient.query(YearlyExpenseQuery(user_id.toString())).await()

        } catch (e: ApolloException) {
            Log.d("YearlyExpense", "Failure", e)
            null
        }

        val properties = response?.data?.user?.properties
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/expenses.csv"
        val header = "Address,Type,Amount,Date"

        if(properties != null && !response.hasErrors()){
            exportExpenseCSV(properties, header, filename)
        }

    }

    suspend fun generateIncomeReport(context: Context, user_id: Int){
        val response = try {
            apolloClient.query(YearlyIncomeQuery(user_id.toString())).await()

        } catch (e: ApolloException) {
            Log.d("YearlyIncome", "Failure", e)
            null
        }

        val properties = response?.data?.user?.properties
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/income.csv"
        val header = "Address,Type,Amount,Date"

        if (properties != null && !response.hasErrors()){
            exportIncomeCSV(properties, header, filename)
        }
    }

    suspend fun generateMaintenanceHistoryReport(context: Context, propeprty_id: Int){
        val response = try {
            apolloClient.query(MaintenanceHistoryQuery(propeprty_id.toString())).await()

        } catch (e: ApolloException) {
            Log.d("MaintenanceHistory", "Failure", e)
            null
        }

        val maintenances = response?.data?.property?.maintenances
        val header = "Description,Contractor,Date"
        val title = response?.data?.property?.st_address
        var filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/" + title + "history.csv"

        if(maintenances != null && !response.hasErrors()){
            exportHistoryCSV(maintenances,header, title, filename)
        }
    }


    suspend fun generateYearlyMaintenanceReport(context: Context, propeprty_id: Int, year: Int){
        val response = try {
            apolloClient.query(YearlyMaintenanceQuery(propeprty_id.toString(), year)).await()
        } catch (e: ApolloException) {
            Log.d("YearlyMaintenance", "Failure", e)
            null
        }

        val maintenances = response?.data?.allMaintenances
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/" + year + "yearlyMaint.csv"
        val header = "Description,Contractor,Date"
        val title = maintenances?.get(0)?.property?.st_address + "  " + year.toString()

        if (maintenances != null && !response.hasErrors()){
            exportYearlyMaintCSV(maintenances, header, title, filename)
        }
    }

    private fun exportYearlyMaintCSV(maintenances: List<YearlyMaintenanceQuery.AllMaintenance?>, header: String, title: String, filename: String) {
        var fileWriter = FileWriter(filename)
        Log.d("Filename", "$filename")
        fileWriter.append(title)
        fileWriter.append('\n')
        fileWriter.append(header)
        fileWriter.append('\n')


        for (item in maintenances){
            if (item != null){
                fileWriter.append(item.description)
                fileWriter.append('\n')
                fileWriter.append(item.contractor)
                fileWriter.append('\n')
                fileWriter.append(item.date_finished)
                fileWriter.append('\n')

            }
        }

        fileWriter.flush()
        fileWriter.close()
    }


    private fun exportHistoryCSV(maintenances: List<MaintenanceHistoryQuery.Maintenance?>, header: String, title: String?, filename: String) {
        var fileWriter = FileWriter(filename)
        Log.d("Filename", "$filename")
        fileWriter.append(title)
        fileWriter.append('\n')
        fileWriter.append(header)
        fileWriter.append('\n')


        for (item in maintenances){
            if (item != null){
                fileWriter.append(item.description)
                fileWriter.append('\n')
                fileWriter.append(item.contractor)
                fileWriter.append('\n')
                fileWriter.append(item.date_finished)
                fileWriter.append('\n')

            }
        }

        fileWriter.flush()
        fileWriter.close()
    }

    private fun exportIncomeCSV(data: List<YearlyIncomeQuery.Property?>, header: String, filename: String) {

        var fileWriter = FileWriter(filename)
        Log.d("Filename", "$filename")
        fileWriter.append(header)
        fileWriter.append('\n')

        for (property in data) {
            if(property != null ) {
                if (property.incomes != null) {
                    for (item in property.incomes) {
                        fileWriter.append(property.st_address)
                        fileWriter.append(",")
                        fileWriter.append(item?.type)
                        fileWriter.append(",")
                        fileWriter.append(item?.amt_received)
                        fileWriter.append(",")
                        fileWriter.append(item?.date_received)
                        fileWriter.append("\n")
                    }
                }
            }
        }
        fileWriter.flush()
        fileWriter.close()

    }


    private fun exportExpenseCSV(data: List<YearlyExpenseQuery.Property?>?, header: String, filename: String){

        var fileWriter = FileWriter(filename)
        Log.d("Filename", "$filename")
        fileWriter.append(header)
        fileWriter.append('\n')

        if (data != null) {
            for (property in data) {
                if(property != null ) {
                    if (property.expenses != null) {
                        for (item in property.expenses) {
                            fileWriter.append(property.st_address)
                            fileWriter.append(",")
                            fileWriter.append(item?.type)
                            fileWriter.append(",")
                            fileWriter.append(item?.amount_spent)
                            fileWriter.append(",")
                            fileWriter.append(item?.date_spent)
                            fileWriter.append("\n")
                        }
                    }
                }
            }
        }
        fileWriter.flush()
        fileWriter.close()
    }
}