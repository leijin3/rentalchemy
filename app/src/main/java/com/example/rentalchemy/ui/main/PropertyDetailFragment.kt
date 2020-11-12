package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R

class PropertyDetailFragment : Fragment(){


    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        private const val positionKey = "positionKey"

        fun newInstance() = PropertyDetailFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Need a way to tell whether this was called from "Add Property" or by clicking on a
        // property from the list.  For Example, if MainViewModel.selectedProperty == null
        // but we'd need to set it to null on clicked "Add Property" button
        if(true){
            //TODO: Set name of button to "Save Property Detail"
            //On click, get stuff from form and call viewModel.addProperty
        } else {

            var currentProperty = MainViewModel.selectedProperty

            var streetTV : TextView = view.findViewById(R.id.detail_street)
            var cityTV : TextView = view.findViewById(R.id.detail_city)
            var stateTV : TextView = view.findViewById(R.id.detail_state)
            var postalCodeTV : TextView = view.findViewById(R.id.detail_zip)
            var rentAmtTV : TextView = view.findViewById(R.id.detail_rent_amt)
            // TODO: Do stuff for spinner for detail_property_type
            var sqftTV : TextView = view.findViewById(R.id.detail_sqft)
            var bedsTV : TextView = view.findViewById(R.id.detail_num_beds)
            var bathsTV : TextView = view.findViewById(R.id.detail_num_baths)
            var parkingTV : TextView = view.findViewById(R.id.detail_parking)
            var costBasisTV : TextView = view.findViewById(R.id.detail_cost_basis)
            var yearBuiltTV : TextView = view.findViewById(R.id.detail_year_built)
            var dateAcqTV : TextView = view.findViewById(R.id.detail_date_acquired)

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
            }

            //TODO: disable all the editTexts

            //detail_edit_saveBut.setOnClickListener{
            // change name of button, enable editTexts, cancel this listener and make a new one
            // that saves the edits (making sure to change the number strings back to ints)
            // and re-sets the button name and click listener}


        }
    }
}