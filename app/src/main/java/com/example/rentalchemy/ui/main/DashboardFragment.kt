package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.forms.ItemListFragment
import com.example.rentalchemy.ui.forms.TenantInfoFragment

class DashboardFragment : Fragment() {


    companion object {
        fun newInstance() = DashboardFragment()
        const val propertyDetailKey = "PropertyDetail"
        const val tenantInfoKey = "TenantInfo"
        const val maintenanceKey = "Maintenance"
        const val applianceKey = "Appliance"
        const val incomeKey = "Income"
        const val expenseKey = "Expense"
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.property_dashboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val (_, _, _, _, streetAddress, city, state, _) = MainViewModel.selectedProperty!!
        view.findViewById<TextView>(R.id.dashboard_addressTV).text = streetAddress
        view.findViewById<TextView>(R.id.dashboard_cityTV).text = city
        view.findViewById<TextView>(R.id.dashboard_stateTV).text = state

        view.findViewById<Button>(R.id.property_detailBut).apply {
            setOnClickListener {
                val propertyDetailFragment = PropertyDetailFragment.newInstance(false)
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

        view.findViewById<Button>(R.id.maintenanceBut).apply {
            setOnClickListener {
                val maintListFragment = ItemListFragment.newInstance("Maintenance")
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(maintenanceKey)
                    .replace(R.id.container, maintListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        view.findViewById<Button>(R.id.appliancesBut).apply {
            setOnClickListener {
                val appliListFragment = ItemListFragment.newInstance("Appliance")
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(applianceKey)
                    .replace(R.id.container, appliListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        view.findViewById<Button>(R.id.incomeBut).apply {
            setOnClickListener {
                val incomeListFragment = ItemListFragment.newInstance("Income")
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(incomeKey)
                    .replace(R.id.container, incomeListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        view.findViewById<Button>(R.id.expensesBut).apply {
            setOnClickListener {
                val expenseListFragment = ItemListFragment.newInstance("Expense")
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(expenseKey)
                    .replace(R.id.container, expenseListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        view.findViewById<Button>(R.id.reportBut).apply {
            setOnClickListener{
                viewModel.generateExpenseReport(requireContext())
            }
        }

    }

}



