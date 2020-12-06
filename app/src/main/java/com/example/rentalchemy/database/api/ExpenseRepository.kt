package com.example.rentalchemy.database.api

import com.example.rentalchemy.database.model.Expense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseRepository(private val jsApi: JsonServerApi) {

    //functions to serve data to the ViewModel


    fun create(expenseInfo: Expense, onResult: (Expense?) -> Unit) {
        jsApi.createExpense(expenseInfo).enqueue(
            object : Callback<Expense> {
                override fun onFailure(call: Call<Expense>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Expense>, response: Response<Expense>) {
                    val addExpense = response.body()
                    onResult(addExpense)


                }
            }
        )
    }

    fun update(id: Long, newExpense: Expense, onResult: (Expense?) -> Unit) {
        jsApi.updateExpense(id, newExpense).enqueue(
            object : Callback<Expense> {
                override fun onFailure(call: Call<Expense>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Expense>, response: Response<Expense>) {
                    onResult(newExpense)
                }
            }
        )
    }

    suspend fun getExpenseList(propertyId: Long): List<Expense> {
        return jsApi.getExpenseList(propertyId)
    }

    fun delete(propertyId: Long, onResult: (Expense?) -> Unit) {
        jsApi.deleteExpense(propertyId).enqueue(
            object : Callback<Expense> {
                override fun onFailure(call: Call<Expense>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Expense>, response: Response<Expense>) {

                }
            }
        )
    }
}