package com.e.Sport.di.screens

import com.e.Sport.MainActivity
import com.e.Sport.di.anotations.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules =[MainActivityFragmentsBuilder::class] )
    abstract fun contributeMainActivity():MainActivity
}