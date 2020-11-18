package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel
import java.lang.Float.parseFloat


class ApplianceFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val applianceTypes: Array<String> by lazy {
        resources.getStringArray(R.array.appliance_types)
    }

    companion object{
        fun newInstance()  = ApplianceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.appliance_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val applianceTypeSpinner: Spinner = view.findViewById(R.id.appliance_type)
        val applianceTypeAdapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.appliance_types, android.R.layout.simple_spinner_item
        )
        applianceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        applianceTypeSpinner.adapter = applianceTypeAdapter

        val priceTV = view.findViewById<TextView>(R.id.appliance_price)
        val dateTV = view.findViewById<TextView>(R.id.appliance_date_purchased)
        val warrantyTV = view.findViewById<TextView>(R.id.appliance_warranty)
        val addressTV = view.findViewById<TextView>(R.id.appliance_address)
        val saveBut = view.findViewById<Button>(R.id.appliance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener{
            viewModel.addAppliance(applianceTypeSpinner.selectedItem.toString(), parseFloat(priceTV.text.toString()),
                dateTV.text.toString(), warrantyTV.text.toString())
            parentFragmentManager.popBackStack()
        }
    }
}