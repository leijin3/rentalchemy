package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.forms.TenantInfoFragment

class DashboardFragment : Fragment() {


    companion object {
        fun newInstance() = DashboardFragment()
        const val propertyDetailKey = "PropertyDetail"
        const val tenantInfoKey = "TenantInfo"
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
        val (_, _, streetAddress, city, state, _) = MainViewModel.selectedProperty!!
        view.findViewById<TextView>(R.id.dashboard_addressTV).text = streetAddress
        view.findViewById<TextView>(R.id.dashboard_cityTV).text = city
        view.findViewById<TextView>(R.id.dashboard_stateTV).text = state

        view.findViewById<Button>(R.id.property_detailBut).apply {
            setOnClickListener {
                val propertyDetailFragment = PropertyDetailFragment.newInstance()
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(propertyDetailKey)
                    .replace(R.id.container, propertyDetailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        view.findViewById<Button>(R.id.tenant_infoBut).apply {
            setOnClickListener {
                val tenantInfoFragment = TenantInfoFragment.newInstance()
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(tenantInfoKey)
                    .replace(R.id.container, tenantInfoFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }


    }

}



