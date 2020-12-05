package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt


class ApplianceFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var isEditing: Boolean = false
    private val applianceTypes: Array<String> by lazy {
        resources.getStringArray(R.array.appliance_types)
    }

    companion object {
        const val editKey = "isEditing"
        fun newInstance(isEditing: Boolean): ApplianceFragment {
            val frag = ApplianceFragment()
            val bundle = Bundle()
            bundle.putBoolean(editKey, isEditing)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val monthTV = view.findViewById<TextView>(R.id.appliance_month_purchased)
        val yearTV = view.findViewById<TextView>(R.id.appliance_year_purchased)
        val warrantyTV = view.findViewById<TextView>(R.id.appliance_warranty)
        val addressTV = view.findViewById<TextView>(R.id.appliance_address)
        val saveBut = view.findViewById<Button>(R.id.appliance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener {
            val validPrice = (priceTV.text.toString()).matches("\\d+(\\.\\d{1,2})?".toRegex())
            val validYear = (yearTV.text.toString()).matches("\\d{4}".toRegex())

            if(validPrice && validYear) {
                viewModel.addAppliance( parseInt(yearTV.text.toString()), monthTV.text.toString(),
                        applianceTypeSpinner.selectedItem.toString(), parseFloat(priceTV.text.toString()),
                        dateTV.text.toString(), warrantyTV.text.toString()
                )
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(this.context, "Enter a valid price and year", Toast.LENGTH_LONG).show()
            }
        }
    }
}