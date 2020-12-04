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

    suspend fun generateExpenseReport(context: Context, propeprty_id: String, year: Int){
        val response = try {
            apolloClient.query(YearlyExpenseQuery(propeprty_id, year)).await()

        } catch (e: ApolloException) {
            Log.d("YearlyExpense", "Failure", e)
            null
        }

        val expenses = response?.data?.allExpenses
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/expenses.csv"
        val title = expenses?.get(0)?.property?.st_address

        if(expenses != null && !response.hasErrors()){
                exportExpenseCSV(expenses, title.toString(), filename)
        }

    }

    suspend fun generateIncomeReport(context: Context, property_id: String, year: Int){
        val response = try {
            apolloClient.query(YearlyIncomeQuery(property_id, year)).await()

        } catch (e: ApolloException) {
            Log.d("YearlyIncome", "Failure", e)
            null
        }

        val incomes = response?.data?.allIncomes
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/income.csv"
        val title = incomes?.get(0)?.property?.st_address

        if (incomes != null && !response.hasErrors()){
            exportIncomeCSV(incomes, title.toString(), filename)
        }
    }

    suspend fun generateMaintenanceHistoryReport(context: Context, propeprty_id: String){
        val response = try {
            apolloClient.query(MaintenanceHistoryQuery(propeprty_id)).await()

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


    suspend fun generateYearlyMaintenanceReport(context: Context, propeprty_id: String, year: Int){
        val response = try {
            apolloClient.query(YearlyMaintenanceQuery(propeprty_id, year)).await()
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
        Log.d("Filename", filename)
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
        Log.d("Filename", filename)
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

    private fun exportIncomeCSV(data: List<YearlyIncomeQuery.AllIncome?>, title: String, filename: String) {

        val header = "Type,Amount,Date"
        var fileWriter = FileWriter(filename)
        Log.d("Filename", filename)
        fileWriter.append(title)
        fileWriter.append('\n')
        fileWriter.append(header)
        fileWriter.append('\n')

        for (income in data) {
            if(income != null ) {

                fileWriter.append(income.type)
                fileWriter.append(",")
                fileWriter.append(income.amt_received)
                fileWriter.append(",")
                fileWriter.append(income.date_received)
                fileWriter.append("\n")
            }
        }
        fileWriter.flush()
        fileWriter.close()
    }


    private fun exportExpenseCSV(data: List<YearlyExpenseQuery.AllExpense?>?, title: String, filename: String){

        val header = "Type,Amount,Date"
        var fileWriter = FileWriter(filename)
        Log.d("Filename", filename)
        fileWriter.append(title)
        fileWriter.append('\n')
        fileWriter.append(header)
        fileWriter.append('\n')

        if (data != null) {
            for (expense in data) {
                if(expense != null ) {
                        fileWriter.append(expense.type)
                        fileWriter.append(",")
                        fileWriter.append(expense.amount_spent)
                        fileWriter.append(",")
                        fileWriter.append(expense.date_spent)
                        fileWriter.append("\n")

                }

            }
        }
        fileWriter.flush()
        fileWriter.close()
    }
}