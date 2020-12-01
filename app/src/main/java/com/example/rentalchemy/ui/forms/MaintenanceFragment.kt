package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.adapters.MaintListAdapter
import com.example.rentalchemy.ui.adapters.PropertyListAdapter
import com.example.rentalchemy.ui.main.MainViewModel

class MaintenanceFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: MaintListAdapter

    companion object {
        fun newInstance() = MaintenanceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.maintenance_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        initMaintenanceItemObservers()
        MainViewModel.selectedProperty?.let { viewModel.fetchMaintenanceItems(it.id) }

        val addressTV = view.findViewById<TextView>(R.id.maintenance_address)
        val descriptionTV = view.findViewById<TextView>(R.id.description)
        val contractorTV = view.findViewById<TextView>(R.id.contractor)
        val dateFinishedTV = view.findViewById<TextView>(R.id.date_finished)
        val saveBut = view.findViewById<Button>(R.id.maintenance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener {
            viewModel.addMaintenance(
                descriptionTV.text.toString(), contractorTV.text.toString(),
                dateFinishedTV.text.toString()
            )
            parentFragmentManager.popBackStack()
        }
    }

    // Set up the adapter
    private fun initAdapter(root: View) {
        adapter = MaintListAdapter(viewModel)
        val rv = root.findViewById<RecyclerView>(R.id.item_listRV)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }


    private fun initMaintenanceItemObservers() {
        viewModel.observeMaintenanceItems().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }
}