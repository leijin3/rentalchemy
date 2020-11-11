package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.ui.main.MainViewModel

class PropertyListAdapter(
    private val viewModel: MainViewModel,
    private val propertyClickListener: () -> Unit
) : ListAdapter<Property, PropertyListAdapter.VH>(PropertyDiff()) {
    class PropertyDiff : DiffUtil.ItemCallback<Property>() {

        override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.streetAddress == newItem.streetAddress
                    && oldItem.city == newItem.city
                    && oldItem.state == newItem.state
        }
    }

    //View Holder
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var addressTV = itemView.findViewById<TextView>(R.id.row_address)
        private var cityTV = itemView.findViewById<TextView>(R.id.row_city)
        private var stateTV = itemView.findViewById<TextView>(R.id.row_state)


        fun bind(item: Property) {
            addressTV.text = item.streetAddress
            cityTV.text = item.city
            stateTV.text = item.state
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.property_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            propertyClickListener()
            MainViewModel.selectedProperty = getItem(position)
        }
    }
}