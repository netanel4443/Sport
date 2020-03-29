package com.e.Sport.di.screens

import com.e.Sport.di.anotations.FragmentScope
import com.e.Sport.ui.fragments.GroceryFragment
import com.e.Sport.ui.fragments.ListOfMenusFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuilder {

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindGroceryFragment():GroceryFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindListOfMenusFragment():ListOfMenusFragment
}