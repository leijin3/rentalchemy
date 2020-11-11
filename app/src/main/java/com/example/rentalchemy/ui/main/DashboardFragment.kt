package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rentalchemy.R

class DashboardFragment : Fragment() {


    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.property_dashboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val (_, _, streetAddress, city, state, _) = MainViewModel.getProperty()!!
        view.findViewById<TextView>(R.id.dashboard_addressTV).text = streetAddress
        view.findViewById<TextView>(R.id.dashboard_cityTV).text = city
        view.findViewById<TextView>(R.id.dashboard_stateTV).text = state


    }


}



