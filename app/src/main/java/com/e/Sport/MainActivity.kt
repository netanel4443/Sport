package com.e.Sport

import android.os.Bundle
import com.e.Sport.ui.BaseActivity
import com.e.Sport.ui.fragments.BmrCalculatorFragment
import com.e.Sport.ui.fragments.ListOfMenusFragment
import com.e.Sport.ui.fragments.ListOfProgramsFragment
import com.e.Sport.utils.addFragment
import com.e.Sport.utils.rxutils.throttleFirstClick
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

  // private val viewModel:BmrCalculatorViewModel by lazy(this::getViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        disposeable.add(RxView.clicks(groceriesMenuCard).throttleFirstClick().subscribe {
            addFragment(ListOfMenusFragment(),R.id.frame_layout,"ListOfMenusFragment")
        })

        disposeable.add(RxView.clicks(trainingProgramCard).throttleFirstClick().subscribe{
            addFragment(ListOfProgramsFragment(),R.id.frame_layout,"ListOfProgramsFragment")
        })

        disposeable.add(RxView.clicks(bmrCalculatorCard).throttleFirstClick().subscribe {
            addFragment(BmrCalculatorFragment(),R.id.frame_layout,"BmrCalculatorFragment")
        })

    }

}


