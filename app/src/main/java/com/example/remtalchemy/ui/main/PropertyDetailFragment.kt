package com.example.remtalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.remtalchemy.R

class PropertyDetailFragment : Fragment(){

    companion object {
        fun newInstance() = PropertyDetailFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }




}