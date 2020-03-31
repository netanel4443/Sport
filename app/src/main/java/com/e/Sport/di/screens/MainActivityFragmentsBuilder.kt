package com.e.Sport.di.screens

import com.e.Sport.di.anotations.FragmentScope
import com.e.Sport.ui.fragments.GroceriesFragment
import com.e.Sport.ui.fragments.ListOfMenusFragment
import com.e.Sport.ui.fragments.ListOfProgramsFragment
import com.e.Sport.ui.fragments.TrainingProgramFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuilder {

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindGroceryFragment():GroceriesFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindListOfMenusFragment():ListOfMenusFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindListOfPRogramsFragment ():ListOfProgramsFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindTrainingProgramFragment ():TrainingProgramFragment


}