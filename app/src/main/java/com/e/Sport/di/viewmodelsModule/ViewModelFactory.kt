package com.e.Sport.di.viewmodelsModule

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactory {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelProvideFactory): ViewModelProvider.Factory


}