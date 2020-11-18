package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.Expense
import com.example.rentalchemy.ui.main.MainViewModel

class ExpenseListAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Expense, ExpenseListAdapter.VH>(ExpenseDiff()){
    class ExpenseDiff: DiffUtil.ItemCallback<Expense>() {

        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.amount_spent == newItem.amount_spent
                    && oldItem.date_spent == newItem.date_spent
                    && oldItem.receipt_url == newItem.receipt_url
                    && oldItem.type == newItem.type
        }
    }

    //TODO :  finish after writing xml
}