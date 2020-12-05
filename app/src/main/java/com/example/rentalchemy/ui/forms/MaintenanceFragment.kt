package com.example.rentalchemy.ui.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import com.example.rentalchemy.ui.main.MainViewModel
import java.lang.Float

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
        val saveBut = view.findViewById<Button>(R.id.maintenance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"
        val propertyId = MainViewModel.selectedProperty?.id!!

        fun enableEditTexts() {
            isEditing = true
            descriptionTV.isEnabled = isEditing
            contractorTV.isEnabled = isEditing
            dateFinishedTV.isEnabled = isEditing
            monthTV.isEnabled = isEditing
            yearTV.isEnabled = isEditing
            saveBut.text = "Save Maintenance Item"

        }

        fun disableEditTexts() {
            isEditing = false
            descriptionTV.isEnabled = isEditing
            contractorTV.isEnabled = isEditing
            dateFinishedTV.isEnabled = isEditing
            monthTV.isEnabled = isEditing
            yearTV.isEnabled = isEditing
            saveBut.text = "Edit Maintenance Item"

        }

        saveBut.setOnClickListener {
            if (isEditing) {
                viewModel.createMaintenanceItem(
                    yearTV.text.toString().toInt(),
                    monthTV.text.toString().toInt(),
                    descriptionTV.text.toString(),
                    contractorTV.text.toString(),
                    dateFinishedTV.text.toString(),
                )
                disableEditTexts()
            } else {
                enableEditTexts()
            }

        }
    }
}
