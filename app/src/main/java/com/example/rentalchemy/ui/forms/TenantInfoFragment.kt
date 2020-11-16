package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel


class TenantInfoFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = TenantInfoFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.tenant_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    //How to get tenant info from viewModel?

        //Show tenant info in view
        //Disable Edit Texts
        //set listener for "Edit" button

    }
}