package com.example.rentalchemy.ui.main

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
import com.example.rentalchemy.database.model.Property
import com.example.rentalchemy.ui.main.MainViewModel.Companion.selectedProperty
import java.lang.Integer.parseInt

class PropertyDetailFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
    private var isEditing: Boolean = false
    private val propertyTypes: Array<String> by lazy {
        resources.getStringArray(R.array.property_types)
    }

    companion object {
        val editKey = "isEditing"
        fun newInstance(isEditing: Boolean): PropertyDetailFragment {
            val frag = PropertyDetailFragment()
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
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set up spinner for Property Type
        val propertyTypeSpinner: Spinner = view.findViewById(R.id.detail_property_type)
        val propertyTypeAdapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.property_types, android.R.layout.simple_spinner_item
        )
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        propertyTypeSpinner.adapter = propertyTypeAdapter

        isEditing = arguments?.getBoolean(editKey) ?: false

        val editSaveBut = view.findViewById<Button>(R.id.detail_edit_saveBut)

        if (isEditing) {
            editSaveBut.text = "Save Property Detail"
        }

        var streetTV: TextView = view.findViewById(R.id.detail_street)
        var cityTV: TextView = view.findViewById(R.id.detail_city)
        var stateTV: TextView = view.findViewById(R.id.detail_state)
        var postalCodeTV: TextView = view.findViewById(R.id.detail_zip)
        var rentAmtTV: TextView = view.findViewById(R.id.detail_rent_amt)
        var sqftTV: TextView = view.findViewById(R.id.detail_sqft)
        var bedsTV: TextView = view.findViewById(R.id.detail_num_beds)
        var bathsTV: TextView = view.findViewById(R.id.detail_num_baths)
        var parkingTV: TextView = view.findViewById(R.id.detail_parking)
        var costBasisTV: TextView = view.findViewById(R.id.detail_cost_basis)
        var yearBuiltTV: TextView = view.findViewById(R.id.detail_year_built)
        var dateAcqTV: TextView = view.findViewById(R.id.detail_date_acquired)


        if (MainViewModel.selectedProperty != null) {
            //if there is no current property, this was started from "Add Property".  start with blank, editable form.

            var currentProperty = MainViewModel.selectedProperty

            currentProperty?.apply {
                streetTV.text = this.streetAddress
                cityTV.text = this.city
                stateTV.text = this.state
                postalCodeTV.text = this.zip
                rentAmtTV.text = this.rent_amt
                sqftTV.text = this.sqft.toString()
                bedsTV.text = this.num_beds.toString()
                bathsTV.text = this.num_baths.toString()
                parkingTV.text = this.parking.toString()
                costBasisTV.text = this.cost_basis
                yearBuiltTV.text = this.year_built.takeLast(4)
                dateAcqTV.text = this.date_acquired
                setSpinnerToType(propertyTypeSpinner, this.propertyType)
            }
        }

        fun enableEditTexts() {
            streetTV.isEnabled = true
            cityTV.isEnabled = true
            stateTV.isEnabled = true
            postalCodeTV.isEnabled = true
            rentAmtTV.isEnabled = true
            sqftTV.isEnabled = true
            bedsTV.isEnabled = true
            bathsTV.isEnabled = true
            parkingTV.isEnabled = true
            costBasisTV.isEnabled = true
            yearBuiltTV.isEnabled = true
            dateAcqTV.isEnabled = true
            propertyTypeSpinner.isEnabled = true
        }

        fun disableEditTexts() {
            streetTV.isEnabled = false
            cityTV.isEnabled = false
            stateTV.isEnabled = false
            postalCodeTV.isEnabled = false
            rentAmtTV.isEnabled = false
            sqftTV.isEnabled = false
            bedsTV.isEnabled = false
            bathsTV.isEnabled = false
            parkingTV.isEnabled = false
            costBasisTV.isEnabled = false
            yearBuiltTV.isEnabled = false
            dateAcqTV.isEnabled = false
            propertyTypeSpinner.isEnabled = false
        }

        editSaveBut.setOnClickListener {
            if (isEditing) { //Clicked "Save"
                val newProperty: Property = Property(
                    selectedProperty!!.id,
                    selectedProperty!!.landlordID,
                    streetTV.text.toString(),
                    cityTV.text.toString(),
                    stateTV.text.toString(),
                    postalCodeTV.text.toString(),
                    rentAmtTV.text.toString(),
                    propertyTypeSpinner.selectedItem.toString(),
                    parseInt(sqftTV.text.toString()),
                    parseInt(bedsTV.text.toString()),
                    parseInt(bathsTV.text.toString()),
                    costBasisTV.text.toString(),
                    dateAcqTV.text.toString(),
                    yearBuiltTV.text.toString(),
                    parseInt(parkingTV.text.toString())
                )
                //How do we set the property id and user Id?
                viewModel.updateProperty(newProperty)
                isEditing = false
                editSaveBut.text = "Edit Property Detail"
                disableEditTexts()
            } else {  //Clicked "Edit"
                isEditing = true
                editSaveBut.text = "Save Property Detail"
                enableEditTexts()
            }
        }
        if (!isEditing) {
            disableEditTexts()
        }
    }

    private fun setSpinnerToType(typeSpinner: Spinner, type: String) {
        val index = propertyTypes.indexOf(type)
        typeSpinner.setSelection(index)
    }


}