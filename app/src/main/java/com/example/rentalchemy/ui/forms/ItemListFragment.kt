package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.database.model.Expense
import com.example.rentalchemy.ui.adapters.AppliListAdapter
import com.example.rentalchemy.ui.adapters.ExpenseListAdapter
import com.example.rentalchemy.ui.adapters.IncomeListAdapter
import com.example.rentalchemy.ui.adapters.MaintListAdapter
import com.example.rentalchemy.ui.main.MainViewModel

class ItemListFragment : Fragment() {

    companion object {
        val typeKey = "itemType"
        fun newInstance(itemType: String): ItemListFragment {
            val frag = ItemListFragment()
            val bundle = Bundle()
            bundle.putString(typeKey, itemType)
            frag.arguments = bundle
            return frag
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    //private lateinit var adapter: RecyclerView.Adapter
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getString(typeKey) ?: "Unknown Type"

        val itemTitleTV = view.findViewById<TextView>(R.id.item_list_title)
        val addressTV = view.findViewById<TextView>(R.id.item_address)
        val addBut = view.findViewById<Button>(R.id.add_itemBut)
        val delBut = view.findViewById<Button>(R.id.delete_itemBut)

        itemTitleTV.text = type
        addressTV.text = MainViewModel.selectedProperty?.streetAddress

        initAdapter(view, type)
        addBut.setOnClickListener { addButtonListener(type) }
        delBut.setOnClickListener { delButtonListener(type) }

    }

    private fun clickExpenseRow(){
        parentFragmentManager
                .beginTransaction()
                .addToBackStack("Expense")
                .replace(R.id.container, ExpenseFragment.newInstance(false))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    private fun initAdapter(root: View, type: String) {
        val adapter = when (type) {
            "Maintenance" -> MaintListAdapter(viewModel)
            "Appliance" -> AppliListAdapter(viewModel)
            "Income" -> IncomeListAdapter(viewModel)
            "Expense" -> ExpenseListAdapter(viewModel, ::clickExpenseRow)
            else -> IncomeListAdapter(viewModel)
        }
        val rv = root.findViewById<RecyclerView>(R.id.item_listRV)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun addButtonListener(type: String) {
        val itemFrag =
            when (type) {
                "Maintenance" -> MaintenanceFragment.newInstance()
                "Appliance" -> ApplianceFragment.newInstance()
                "Income" -> IncomeFragment.newInstance()
                "Expense" -> ExpenseFragment.newInstance(true)
                else -> Fragment()
            }
        parentFragmentManager
            .beginTransaction()
            .add(R.id.container, itemFrag)
            .addToBackStack(type)
            .commit()
    }

    private fun delButtonListener(type: String) {
        //TODO: WRITE ME
        //DELETE only selected item.

    }

}