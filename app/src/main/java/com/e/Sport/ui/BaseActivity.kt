package com.e.Sport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity:DaggerAppCompatActivity() {

    @Inject lateinit var provider:ViewModelProvider.Factory

   protected inline fun <reified T:ViewModel>getViewModel():T=
      ViewModelProvider(this,provider)[T::class.java]

}