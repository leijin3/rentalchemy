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

class IncomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var isEditing: Boolean = false

    private val incomeTypes: Array<String> by lazy {
        resources.getStringArray(R.array.income_types)
    }

    companion object {
        const val editKey = "isEditing"
        fun newInstance(isEditing: Boolean): IncomeFragment {
            val frag = IncomeFragment()
            val bundle = Bundle()
            bundle.putBoolean(MaintenanceFragment.editKey, isEditing)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.income_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val incomeTypeSpinner: Spinner = view.findViewById(R.id.income_type)
        val incomeTypeAdapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.income_types, android.R.layout.simple_spinner_item
        )
        incomeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        incomeTypeSpinner.adapter = incomeTypeAdapter

        val amountTV = view.findViewById<TextView>(R.id.income_amt_received)
        val dateTV = view.findViewById<TextView>(R.id.income_date_received)
        val yearTV = view.findViewById<TextView>(R.id.income_year_received)
        val monthTV = view.findViewById<TextView>(R.id.income_month_received)
        val addressTV = view.findViewById<TextView>(R.id.income_address)
        val saveBut = view.findViewById<Button>(R.id.income_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener {
            val validAmount = (amountTV.text.toString()).matches("\\d+(\\.\\d{1,2})?".toRegex())
            val validMonth = (monthTV.text.toString()).matches("\\d{1,2}".toRegex())
            val validYear = (yearTV.text.toString()).matches("\\d{4}".toRegex())

            if (validAmount && validMonth && validYear) {
                viewModel.createIncomeItem(
                    parseInt(yearTV.text.toString()), parseInt(monthTV.text.toString()),
                    incomeTypeSpinner.selectedItem.toString(), parseFloat(amountTV.text.toString()),
                    dateTV.text.toString()
                )
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    this.context,
                    "Enter a valid amount, month, and year",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}