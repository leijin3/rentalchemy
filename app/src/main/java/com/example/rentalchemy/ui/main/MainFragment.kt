package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var propertyListFragment: PropertyListFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enterButton = view.findViewById<Button>(R.id.usernameBut)
        var userNameET = view.findViewById<EditText>(R.id.usernameET)

        fun doSignIn(){
            if (userNameET.text.isNotEmpty()) {
                //TODO:  implement checking the username and query for UID, set UID in viewModel
                propertyListFragment = PropertyListFragment.newInstance()
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, propertyListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        usernameET.setOnEditorActionListener{view, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    doSignIn()
                    true
                }
                else -> false
            }
        }

        enterButton.setOnClickListener {
            doSignIn()
        }
    }


}