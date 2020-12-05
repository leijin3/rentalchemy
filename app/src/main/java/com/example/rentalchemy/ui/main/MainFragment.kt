package com.example.rentalchemy.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R

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
        val userNameET = view.findViewById<EditText>(R.id.usernameET)

        userNameET.setOnEditorActionListener { /*v*/_, actionId, event ->
            // If user has pressed enter,
            // or if they hit the soft keyboard "GO TO MY PROPERTIES" button
            if ((event != null
                        && (event.action == KeyEvent.ACTION_DOWN)
                        && (event.keyCode == KeyEvent.KEYCODE_ENTER))
                || (actionId == EditorInfo.IME_ACTION_DONE)
            ) {
                hideKeyboard()
                enterButton.callOnClick()
            }
            false
        }

        fun doSignIn() {
            if (userNameET.text.isNotEmpty()) {
                viewModel.getUserId(userNameET.text.toString()).invokeOnCompletion {

                    propertyListFragment = PropertyListFragment.newInstance()
                    parentFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, propertyListFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
            }
        }

        enterButton.setOnClickListener {
            doSignIn()
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}