package com.e.Sport.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.e.Sport.R
import com.e.Sport.viewmodels.BmrCalculatorViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_bmr_calculator.*
import kotlinx.android.synthetic.main.fragment_bmr_calculator.view.*
import javax.inject.Inject

class BmrCalculatorFragment : DaggerFragment() {

    private var ctx:Context?=null
    lateinit var viewModel: BmrCalculatorViewModel

    @Inject lateinit var provider:ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_bmr_calculator, container, false)
        val sexSpinner=view.sexSpinner
        viewModel= ViewModelProvider(activity!!,provider)[BmrCalculatorViewModel::class.java]

        initDropDown(sexSpinner)

        view.measureBtn.setOnClickListener {
            measure()
        }

        view.imperialMetricBtn.setOnClickListener {
            viewModel.switchMeasureType(imperialMetricBtn.text.toString())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.calculateBmr().observe(this, Observer {
            measureResultTview.text="Bmr : $it \nRMR : ${it*1.2}"
        })
        viewModel.switchMeasureType().observe(this, Observer{
            imperialMetricBtn.text=it.first
            heightCmFt.hint=it.second
            weightKgLb.hint=it.third
        })
    }

    private fun measure() {
        if (age.editText!!.text.isNotBlank() &&
            weightKgLb.editText!!.text.isNotBlank() &&
            heightCmFt.editText!!.text.isNotBlank()){
            val hei=heightCmFt.editText?.text.toString().toInt()
            val ag=age.editText?.text.toString().toInt()
            val wei=weightKgLb.editText?.text.toString().toInt()
            val sex=sexSpinner.selectedItem.toString()
            val measureState=imperialMetricBtn.text.toString()
            viewModel.calculateBmr(sex,wei,hei,ag,measureState)
        }
    }

    private fun initDropDown(sexSpinner:Spinner) {
        val items = listOf("male","female")
        ArrayAdapter(ctx!!, R.layout.support_simple_spinner_dropdown_item, items)
            .also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                sexSpinner.adapter = adapter
            }
    }

    override fun onDetach() {
        super.onDetach()
        ctx=null
    }
}
