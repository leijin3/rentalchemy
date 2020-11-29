package com.example.rentalchemy.database.api

import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rentalchemy.YearlyExpenseQuery
import java.io.FileWriter

class ReportGenerator {

    suspend fun generateReport(context: Context, user_id: Int){
        val response = try {
            apolloClient.query(YearlyExpenseQuery(user_id.toString())).await()

        } catch (e: ApolloException) {
            Log.d("YearlyExpense", "Failure", e)
            null
        }

        val properties = response?.data?.user?.properties
        val filename = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).toString() + "/expenses.csv"
        val header = "Address,Amount,Date"

        if(properties != null && !response.hasErrors()){
            exportExpenseCSV(properties, header, filename)
        }

    }


    fun exportExpenseCSV(data: List<YearlyExpenseQuery.Property?>?, header: String, filename: String){

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