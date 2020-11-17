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

    companion object{
        fun newInstance()  = MaintenanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.maintenance_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressTV = view.findViewById<TextView>(R.id.maintenance_address)
        val descriptionTV = view.findViewById<TextView>(R.id.description)
        val contractorTV = view.findViewById<TextView>(R.id.contractor)
        val date_finishedTV = view.findViewById<TextView>(R.id.date_finished)
        val saveBut = view.findViewById<Button>(R.id.maintenance_saveBut)

        addressTV.text = MainViewModel.selectedProperty?.streetAddress ?: "Address Here"

        saveBut.setOnClickListener{
            viewModel.addMaintenance(descriptionTV.text.toString(), contractorTV.text.toString(),
                date_finishedTV.text.toString())
            parentFragmentManager.popBackStack()
        }
    }
}