package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel

class MaintenanceFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var isEditing: Boolean = false

    companion object {
        const val editKey = "isEditing"
        fun newInstance(isEditing: Boolean): MaintenanceFragment {
            val frag = MaintenanceFragment()
            val bundle = Bundle()
            bundle.putBoolean(editKey, isEditing)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.maintenance_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initAdapter(view)
//        initMaintenanceItemObservers()
//        MainViewModel.selectedProperty?.let { viewModel.fetchMaintenanceItems(it.id) }

        isEditing = arguments?.getBoolean(ExpenseFragment.editKey) ?: false

        val addressTV = view.findViewById<TextView>(R.id.maintenance_address)
        val descriptionTV = view.findViewById<TextView>(R.id.description)
        val contractorTV = view.findViewById<TextView>(R.id.contractor)
        val dateFinishedTV = view.findViewById<TextView>(R.id.date_finished)
        val monthTV = view.findViewById<TextView>(R.id.month_finished)
        val yearTV = view.findViewById<TextView>(R.id.year_finished)
        val saveBut = view.findViewById<Button>(R.id.maintenance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"
        val propertyId = MainViewModel.selectedProperty?.id!!

        fun enableEditTexts() {
            descriptionTV.isEnabled = true
            contractorTV.isEnabled = true
            dateFinishedTV.isEnabled = true
            monthTV.isEnabled = true
            yearTV.isEnabled = true

        }

        fun disableEditTexts() {
            descriptionTV.isEnabled = false
            contractorTV.isEnabled = false
            dateFinishedTV.isEnabled = false
            monthTV.isEnabled = false
            yearTV.isEnabled = false
        }

        if (isEditing) { //Creating new Expense
            saveBut.text = "Save Maintenance Item"
        } else { //Displaying selected Expense
            saveBut.text = "Edit Maintenance Item"
//            val currentExpense = MainViewModel.selectedExpense
//            expenseTypeSpinner.setSelection(expenseTypes.indexOf(currentExpense?.type))
//            amountTV.text = currentExpense?.amount_spent.toString()
//            dateTV.text = currentExpense?.date_spent
//            monthTV.text = currentExpense?.month.toString()
//            yearTV.text = currentExpense?.year.toString()
//            receiptIV.setImageURI(currentExpense?.receipt_url?.toUri())
//            disableEditTexts()
        }
    }
}
