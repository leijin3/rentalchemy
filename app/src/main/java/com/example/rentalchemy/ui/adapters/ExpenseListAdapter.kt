package com.example.rentalchemy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.Expense
import com.example.rentalchemy.ui.main.MainViewModel

class ExpenseListAdapter(
    private val viewModel: MainViewModel,
    private val itemClickListener: () -> Unit,
    private val itemLongClickListener: () -> Unit
) :
    ListAdapter<Expense, ExpenseListAdapter.VH>(ExpenseDiff()) {
    class ExpenseDiff : DiffUtil.ItemCallback<Expense>() {

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

    //View Holder
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var typeTV = itemView.findViewById<TextView>(R.id.row_expense_type)
        private var dateTV = itemView.findViewById<TextView>(R.id.row_date_spent)
        private var amountTV = itemView.findViewById<TextView>(R.id.row_amt_spent)
        private var receiptIV = itemView.findViewById<ImageView>(R.id.row_receipt_image)


        fun bind(item: Expense) {
            typeTV.text = item.type
            dateTV.text = item.date_spent
            amountTV.text = item.amount_spent.toString()
            receiptIV.setImageURI(item.receipt_url.toUri())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            MainViewModel.selectedExpense = getItem(position)
            itemClickListener()
        }

        holder.itemView.setOnLongClickListener {
            MainViewModel.selectedExpense = getItem(position)
            itemLongClickListener()
            false
        }
    }
}