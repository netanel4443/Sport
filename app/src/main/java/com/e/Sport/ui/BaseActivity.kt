package com.e.Sport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

abstract class BaseActivity:DaggerAppCompatActivity() {

    protected val disposables=CompositeSubscription()

    @Inject lateinit var provider:ViewModelProvider.Factory

 /*  protected inline fun <reified T:ViewModel> getViewModel():T{
      val t:T by lazy { ViewModelProvider(this,provider)[T::class.java]}
      return t
    }*/

    inline fun <reified T:ViewModel>getViewModel():T=
      ViewModelProvider(this,provider)[T::class.java]


    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}