package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.IncomeItem
import com.example.rentalchemy.ui.main.MainViewModel

class IncomeListAdapter(private val viewModel: MainViewModel) :
    ListAdapter<IncomeItem, IncomeListAdapter.VH>(IncomeItemDiff()){
    class IncomeItemDiff: DiffUtil.ItemCallback<IncomeItem>() {

        override fun areItemsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
            return oldItem.amt_received == newItem.amt_received
                    && oldItem.date_received == newItem.date_received
                    && oldItem.type == newItem.type
        }
    }

//TODO:  Finish after writing XML


}