package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.adapters.PropertyListAdapter


class PropertyListFragment : Fragment() {

    companion object {
        fun newInstance() = PropertyListFragment()
        const val propertyListKey = "PropertyList"
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PropertyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.property_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        initPropertyObservers()
        viewModel.fetchProperties()

        view.findViewById<Button>(R.id.add_propertyBut).apply {
            setOnClickListener {
                MainViewModel.selectedProperty = null
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.container, PropertyDetailFragment.newInstance(isEditing = true))
                    .addToBackStack("PropertyDetail")
                    .commit()
            }
        }
    }

    private fun deleteProperty(propertyID: Long) {
        viewModel.deleteProperty(propertyID)
    }


    // Set up the adapter
    private fun initAdapter(root: View) {
        adapter =
            PropertyListAdapter(viewModel, ::propertyClickListener, ::propertyLongClickListener)
        val rv = root.findViewById<RecyclerView>(R.id.property_listRV)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun initPropertyObservers() {
        viewModel.observeProperties().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun propertyClickListener() {
        // Go to Dashboard Fragment w/ current property
        val dashboardFragment = DashboardFragment.newInstance()
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(propertyListKey)
            .replace(R.id.container, dashboardFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun propertyLongClickListener() {
        // Delete Current Property
        deleteProperty(MainViewModel.selectedProperty!!.id.toLong())
    }
}