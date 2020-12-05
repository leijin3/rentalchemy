package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

class ExpenseFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var isEditing: Boolean = false
    private val expenseTypes: Array<String> by lazy {
        resources.getStringArray(R.array.expense_types)
    }

    companion object {
        const val editKey = "isEditing"
        fun newInstance(isEditing: Boolean): ExpenseFragment {
            val frag = ExpenseFragment()
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

        isEditing = arguments?.getBoolean(editKey) ?: false

        val amountTV = view.findViewById<TextView>(R.id.expense_amt_spent)
        val dateTV = view.findViewById<TextView>(R.id.expense_date_spent)
        val monthTV = view.findViewById<TextView>(R.id.expense_month_spent)
        val yearTV = view.findViewById<TextView>(R.id.expense_year_spent)
        val receiptIV = view.findViewById<ImageView>(R.id.receipt_image)
        val receiptBut = view.findViewById<Button>(R.id.add_receiptBut)
        val addressTV = view.findViewById<TextView>(R.id.expense_address)
        val saveBut = view.findViewById<Button>(R.id.expense_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"
        val propertyId = MainViewModel.selectedProperty?.id!!

        fun enableEditTexts() {
            amountTV.isEnabled = true
            dateTV.isEnabled = true
            monthTV.isEnabled = true
            yearTV.isEnabled = true
            receiptBut.isEnabled = true
            expenseTypeSpinner.isEnabled = true
        }

        fun disableEditTexts() {
            amountTV.isEnabled = false
            dateTV.isEnabled = false
            monthTV.isEnabled = false
            yearTV.isEnabled = false
            receiptBut.isEnabled = false
            expenseTypeSpinner.isEnabled = false
        }

        if (isEditing) { //Creating new Expense
            saveBut.text = "Save Expense"
        } else { //Displaying selected Expense
            saveBut.text = "Edit Expense"
            val currentExpense = MainViewModel.selectedExpense
            expenseTypeSpinner.setSelection(expenseTypes.indexOf(currentExpense?.type))
            amountTV.text = currentExpense?.amount_spent.toString()
            dateTV.text = currentExpense?.date_spent
            monthTV.text = currentExpense?.month.toString()
            yearTV.text = currentExpense?.year.toString()
            receiptIV.setImageURI(currentExpense?.receipt_url?.toUri())
            disableEditTexts()
        }

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

        saveBut.setOnClickListener {
            if (isEditing) { //User is clicking "Save Expense"

                val validPrice = (amountTV.text.toString()).matches("\\d+(\\.\\d{1,2})?".toRegex())
                val validMonth = (monthTV.text.toString()).matches("\\d{1,2}".toRegex())
                val validYear = (yearTV.text.toString()).matches("\\d{4}".toRegex())

                if (validPrice && validMonth && validYear) {
                    viewModel.addExpense(
                        parseInt(yearTV.text.toString()),
                        parseInt(monthTV.text.toString()),
                        propertyId,
                        expenseTypeSpinner.selectedItem.toString(),
                        parseFloat(amountTV.text.toString()),
                        dateTV.text.toString(),
                        receiptURL
                    )
                    viewModel.clearCurrentPhoto()
                    saveBut.text = "Edit Expense"
                    disableEditTexts()
                    isEditing = false
                } else {
                    Toast.makeText(this.context, "Enter a valid price, month, and year.", Toast.LENGTH_LONG).show()
                }
            } else { //User is clicking "Edit Expense"
                saveBut.text = "Save Expense"
                enableEditTexts()
                isEditing = true
            }

        }
    }
}