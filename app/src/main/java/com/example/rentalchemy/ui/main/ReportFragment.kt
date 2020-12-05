package com.example.rentalchemy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rentalchemy.R
import kotlinx.android.synthetic.main.report_fragment.*
import java.lang.Integer.parseInt

class ReportFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    companion object{
        fun newInstance() = ReportFragment()

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.report_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        downloadIncomeBut.setOnClickListener{
            report_income_year.text?.apply {
                viewModel.generateIncomeReport(requireContext(), parseInt(this.toString()))
            }
        }

        downloadExpenseBut.setOnClickListener{
            report_expense_year.text?.apply{
                viewModel.generateExpenseReport(requireContext(), parseInt(this.toString()))
            }
        }

        downloadMaintBut.setOnClickListener {
            report_maint_year.text?.apply {
                viewModel.generateMaintReport(requireContext(), parseInt(this.toString()))
            }
        }

        maintHistoryBut.setOnClickListener {
            viewModel.generateMaintHistory(requireContext())
        }

    }

}