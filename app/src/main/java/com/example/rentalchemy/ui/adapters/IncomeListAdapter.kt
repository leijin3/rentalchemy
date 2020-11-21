package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.IncomeItem
import com.example.rentalchemy.ui.main.MainViewModel

class IncomeListAdapter(private val viewModel: MainViewModel) :
    ListAdapter<IncomeItem, IncomeListAdapter.VH>(IncomeItemDiff()) {
    class IncomeItemDiff : DiffUtil.ItemCallback<IncomeItem>() {

        override fun areItemsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
            return oldItem.amt_received == newItem.amt_received
                    && oldItem.date_received == newItem.date_received
                    && oldItem.type == newItem.type
        }
    }

    //View Holder
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var typeTV = itemView.findViewById<TextView>(R.id.row_income_type)
        private var amountTV = itemView.findViewById<TextView>(R.id.row_amt_received)
        private var dateTV = itemView.findViewById<TextView>(R.id.row_date_received)


        fun bind(item: IncomeItem) {
            typeTV.text = item.type
            amountTV.text = item.amt_received.toString()
            dateTV.text = item.date_received
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.income_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        //TODO: onClick for row, highlight selected for possible deletion?
    }


}