package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rentalchemy.R

class PropertyDetailFragment : Fragment(){

    companion object {
        fun newInstance() = PropertyDetailFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (_, _, streetAddress, city, state, zip) = MainViewModel.selectedProperty!!
        view.findViewById<TextView>(R.id.detail_street).text = streetAddress
        view.findViewById<TextView>(R.id.detail_city).text = city
        view.findViewById<TextView>(R.id.detail_state).text = state
        view.findViewById<TextView>(R.id.detail_zip).text = zip

        val propertyEditBtn = view.findViewById<Button>(R.id.edit_detailBut)


    }


}