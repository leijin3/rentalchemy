package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.MaintenanceItem
import com.example.rentalchemy.ui.main.MainViewModel

class MaintListAdapter(
    private val viewModel: MainViewModel,
    private val itemClickListener: () -> Unit,
    private val itemLongClickListener: () -> Unit
) :
    ListAdapter<MaintenanceItem, MaintListAdapter.VH>(MaintenanceDiff()) {
    class MaintenanceDiff : DiffUtil.ItemCallback<MaintenanceItem>() {

        override fun areItemsTheSame(oldItem: MaintenanceItem, newItem: MaintenanceItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MaintenanceItem,
            newItem: MaintenanceItem
        ): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.contractor == newItem.contractor
                    && oldItem.date_finished == newItem.date_finished
        }
    }

    //View Holder
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var descriptionTV = itemView.findViewById<TextView>(R.id.row_description)
        private var contractorTV = itemView.findViewById<TextView>(R.id.row_contractor)
        private var dateTV = itemView.findViewById<TextView>(R.id.row_date_finished)


        fun bind(item: MaintenanceItem) {
            descriptionTV.text = item.description
            contractorTV.text = item.contractor
            dateTV.text = item.date_finished
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.maintenance_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnLongClickListener {
            MainViewModel.selectedMaintenanceItem = getItem(position)
            itemLongClickListener()
            false
        }
    }


}

