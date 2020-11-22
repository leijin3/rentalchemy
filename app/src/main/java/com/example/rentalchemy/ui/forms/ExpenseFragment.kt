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

class ExpenseFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val expenseTypes: Array<String> by lazy {
        resources.getStringArray(R.array.expense_types)
    }

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.expense_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expenseTypeSpinner: Spinner = view.findViewById(R.id.expense_type)
        val expenseTypeAdapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.expense_types, android.R.layout.simple_spinner_item
        )
        expenseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expenseTypeSpinner.adapter = expenseTypeAdapter

        val amountTV = view.findViewById<TextView>(R.id.expense_amt_spent)
        val dateTV = view.findViewById<TextView>(R.id.expense_date_spent)
        val receiptIV = view.findViewById<ImageView>(R.id.receipt_image)
        val receiptBut = view.findViewById<Button>(R.id.add_receiptBut)
        val addressTV = view.findViewById<TextView>(R.id.expense_address)
        val saveBut = view.findViewById<Button>(R.id.expense_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"
        val propertyId = MainViewModel.selectedProperty?.id!!


        var receiptURL = "File Name Here"
        viewModel.observeCurrentPhoto().observe(viewLifecycleOwner, {
            receiptURL = it?.toString() ?: "File Name Here"
            receiptIV.setImageURI(it)
        })

        receiptBut.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.container, CameraFragment.newInstance())
                .addToBackStack("CameraFragment")
                .commit()
        }


        saveBut.setOnClickListener{
            val validPrice = (amountTV.text.toString()).matches("\\d+(\\.\\d{1,2})?".toRegex())

            if(validPrice) {
                viewModel.addExpense(
                propertyId,
                expenseTypeSpinner.selectedItem.toString(), parseFloat(amountTV.text.toString()),
                dateTV.text.toString(), receiptURL
            )
                viewModel.clearCurrentPhoto()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(this.context, "Enter a valid price.", Toast.LENGTH_LONG).show()
            }
       
    }
}