package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.Appliance
import com.example.rentalchemy.ui.main.MainViewModel

class AppliListAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Appliance, AppliListAdapter.VH>(ApplianceDiff()){
    class ApplianceDiff: DiffUtil.ItemCallback<Appliance>() {

        override fun areItemsTheSame(oldItem: Appliance, newItem: Appliance): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Appliance, newItem: Appliance): Boolean {
            return oldItem.date_purchased == newItem.date_purchased
                    && oldItem.type == newItem.type
                    && oldItem.price == newItem.price
                    && oldItem.warranty == newItem.warranty
        }
    }

    //TODO :  finish after writing xml
}