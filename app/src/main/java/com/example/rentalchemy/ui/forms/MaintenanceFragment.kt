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
import com.example.rentalchemy.database.model.MaintenanceItem
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
        isEditing = arguments?.getBoolean(ExpenseFragment.editKey) ?: false

        val addressTV = view.findViewById<TextView>(R.id.maintenance_address)
        val descriptionTV = view.findViewById<TextView>(R.id.description)
        val contractorTV = view.findViewById<TextView>(R.id.contractor)
        val dateFinishedTV = view.findViewById<TextView>(R.id.date_finished)
        val monthTV = view.findViewById<TextView>(R.id.month_finished)
        val yearTV = view.findViewById<TextView>(R.id.year_finished)
        val editSaveBut = view.findViewById<Button>(R.id.maintenance_saveBut)

        if (MainViewModel.selectedProperty != null) {

            val currentMaintenanceItem = MainViewModel.selectedMaintenanceItem

            currentMaintenanceItem?.apply {
                descriptionTV.text = this.description
                contractorTV.text = this.contractor
                dateFinishedTV.text = this.date_finished
                monthTV.text = this.month.toString()
                yearTV.text = this.year.toString()
            }
        }


        if (isEditing) {
            editSaveBut.text = "Save Maintenance Item"
        } else {
            editSaveBut.text = "Edit Maintenance Item"
        }


        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        fun enableEditTexts() {
            isEditing = true
            descriptionTV.isEnabled = isEditing
            contractorTV.isEnabled = isEditing
            dateFinishedTV.isEnabled = isEditing
            monthTV.isEnabled = isEditing
            yearTV.isEnabled = isEditing
            editSaveBut.text = "Save Maintenance Item"

        }

        fun disableEditTexts() {
            isEditing = false
            descriptionTV.isEnabled = isEditing
            contractorTV.isEnabled = isEditing
            dateFinishedTV.isEnabled = isEditing
            monthTV.isEnabled = isEditing
            yearTV.isEnabled = isEditing
            editSaveBut.text = "Edit Maintenance Item"

        }

        editSaveBut.setOnClickListener {
            if (isEditing) {
                viewModel.updateMaintenanceItem(
                    MaintenanceItem(
                        yearTV.text.toString().toInt(),
                        monthTV.text.toString().toInt(),
                        MainViewModel.selectedMaintenanceItem!!.id,
                        MainViewModel.selectedProperty!!.id,
                        descriptionTV.text.toString(),
                        contractorTV.text.toString(),
                        dateFinishedTV.text.toString(),
                    )
                )
                disableEditTexts()
            } else {
                enableEditTexts()
            }

        }
    }
}
