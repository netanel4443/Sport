package com.e.Sport

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.e.Sport.ui.BaseActivity
import com.e.Sport.ui.fragments.ListOfMenusFragment
import com.e.Sport.viewmodels.MainActivityViewModel
import com.e.Sport.utils.addFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

   private val viewModel:MainActivityViewModel by lazy(this::getViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try { this.supportActionBar!!.hide() }
        catch (e: Exception) { }

        initDropDown()

        viewModel.switchMeasureType().observe(this, Observer{
            imperialMetricBtn.text=it.first
            heightCmFt.hint=it.second
            weightKgLb.hint=it.third
        })
        viewModel.calculateBmr().observe(this, Observer {
            measureResultTview.text="Bmr : $it \nRMR : ${it*1.2}"
        })

        addFragmentTview.setOnClickListener{
            addFragment(ListOfMenusFragment(),R.id.frame_layout,"ListOfMenusFragment")
        }

        measureBtn.setOnClickListener {
            measure()
        }

        imperialMetricBtn.setOnClickListener {
            viewModel.switchMeasureType(imperialMetricBtn.text.toString())
        }
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

    private fun initDropDown() {
        val items = listOf("male","female")
         ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)
            .also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                sexSpinner.adapter = adapter
            }
    }
}


