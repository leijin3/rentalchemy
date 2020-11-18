package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.adapters.*
import com.example.rentalchemy.ui.main.MainViewModel

class ItemListFragment : Fragment(){

    companion object{
        val typeKey = "itemType"
        fun newInstance(itemType: String) : ItemListFragment {
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

    private fun initAdapter(root: View, type: String){
        var adapter = when(type) {
            "Maintenance" -> MaintListAdapter(viewModel)
            "Appliance" -> AppliListAdapter(viewModel)
            "Income" -> IncomeListAdapter(viewModel)
            "Expense" -> ExpenseListAdapter(viewModel)
            else -> ExpenseListAdapter(viewModel)
        }
        val rv = root.findViewById<RecyclerView>(R.id.item_listRV)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun addButtonListener(type: String){
        var itemFrag =
            when(type) {
                "Maintenance" -> MaintenanceFragment.newInstance()
                "Appliance" -> ApplianceFragment.newInstance()
                "Income" -> IncomeFragment.newInstance()
                "Expense" -> ExpenseFragment.newInstance()
                else -> Fragment()
        }
        parentFragmentManager
            .beginTransaction()
            .add(R.id.container, itemFrag)
            .addToBackStack(type)
            .commit()
    }

    private fun delButtonListener(type: String){
        //TODO: WRITE ME
        //DELETE only selected item.

    }

}