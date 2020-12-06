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


class TenantInfoFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = TenantInfoFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tenant_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tenant = viewModel.getTenant()
        val property = MainViewModel.selectedProperty

        //Show tenant info in view
        val nameTV: TextView = view.findViewById(R.id.tenant_name)
        val phoneTV: TextView = view.findViewById(R.id.tenant_phone)
        val emailTV: TextView = view.findViewById(R.id.tenant_email)
        val startTV: TextView = view.findViewById(R.id.tenant_lease_start)
        val addressTV: TextView = view.findViewById(R.id.tenant_infoAddress)

        addressTV.text = property?.streetAddress
        tenant.apply {
            nameTV.text = this.name
            phoneTV.text = this.phone
            emailTV.text = this.email
            startTV.text = this.leaseStart
        }

        // Functions to switch from editing/not editing
        fun disableEditTexts() {
            nameTV.isEnabled = false
            phoneTV.isEnabled = false
            emailTV.isEnabled = false
            startTV.isEnabled = false
        }

        fun enableEditTexts() {
            nameTV.isEnabled = true
            phoneTV.isEnabled = true
            emailTV.isEnabled = true
            startTV.isEnabled = true
        }

        //Disable Edit Texts When first starting
        var isEditing = false
        disableEditTexts()

        //set listener for "Edit" button
        val editSaveBut = view.findViewById<Button>(R.id.tenant_edit_saveBut)
        editSaveBut.setOnClickListener {
            if (isEditing) {  //Clicked "Save"
                tenant.name = nameTV.text.toString()
                tenant.phone = phoneTV.toString()
                tenant.email = emailTV.toString()
                tenant.leaseStart = startTV.toString()

//                viewModel.updateTenant(tenant)
                editSaveBut.text = "Edit Tenant Info"
                disableEditTexts()
                isEditing = false

            } else { //Clicked "Edit"
                editSaveBut.text = "Save Tenant Info"
                enableEditTexts()
                isEditing = true
            }
        }
    }
}