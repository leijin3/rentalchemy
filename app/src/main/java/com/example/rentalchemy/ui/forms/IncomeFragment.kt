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

class IncomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val incomeTypes: Array<String> by lazy {
        resources.getStringArray(R.array.income_types)
    }

    companion object {
        fun newInstance() = IncomeFragment()
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
        val addressTV = view.findViewById<TextView>(R.id.income_address)
        val saveBut = view.findViewById<Button>(R.id.income_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener {
            viewModel.addIncome(
                incomeTypeSpinner.selectedItem.toString(), parseFloat(amountTV.text.toString()),
                dateTV.text.toString()
            )
            parentFragmentManager.popBackStack()
        }
    }
}