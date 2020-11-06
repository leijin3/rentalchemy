package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.adapters.PropertyListAdapter


class PropertyListFragment : Fragment() {

    companion object {
        fun newInstance() = PropertyListFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PropertyListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.property_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter(view)
        initPropertyObservers()
        viewModel.netProperties()

    }


    // Set up the adapter
    private fun initAdapter(root: View) {
        adapter = PropertyListAdapter(viewModel)
        val rv = root.findViewById<RecyclerView>(R.id.property_listRV)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun initPropertyObservers() {
        viewModel.observeProperties().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            it.forEach { property -> Log.d("XXX", property.city) }
        })
    }
}