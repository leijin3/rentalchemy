package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {



        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        initAdapter(root)
        initPropertyObservers()
        viewModel.netProperties()




//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // Set up the adapter
//    private fun initAdapter(root: View) {
//        adapter = PostRowAdapter(viewModel)
//        val rv = root.recyclerView
//        rv.adapter = adapter
//        rv.layoutManager = LinearLayoutManager(context)
//    }

    private fun initPropertyObservers() {
        viewModel.observeProperties().observe(viewLifecycleOwner, {
//            adapter.submitList(it)
//            adapter.notifyDataSetChanged()
            it.forEach { property -> Log.d("XXX", property.city) }
        })
    }

}