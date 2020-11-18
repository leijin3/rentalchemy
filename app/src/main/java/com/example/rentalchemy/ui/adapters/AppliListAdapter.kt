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
import com.example.rentalchemy.database.model.MaintenanceItem
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
    //View Holder
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var typeTV = itemView.findViewById<TextView>(R.id.row_appliance_type)
        private var priceTV = itemView.findViewById<TextView>(R.id.row_price)
        private var dateTV = itemView.findViewById<TextView>(R.id.row_date_purchased)
        private var warrantyTV = itemView.findViewById<TextView>(R.id.row_warranty)


        fun bind(item: Appliance) {
            typeTV.text = item.type
            priceTV.text = item.price.toString()
            dateTV.text = item.date_purchased
            warrantyTV.text = item.warranty
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.appliance_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        //TODO: onClick for row, highlight selected for possible deletion?
    }
}