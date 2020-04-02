package com.e.Sport.di.viewmodelsModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.Sport.di.anotations.ViewModelKey
import com.e.Sport.viewmodels.GroceryViewModel
import com.e.Sport.viewmodels.BmrCalculatorViewModel
import com.e.Sport.viewmodels.ProgramViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BmrCalculatorViewModel::class)
    abstract fun bindMainActivityViewModel(bmrCalculatorViewModel: BmrCalculatorViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroceryViewModel::class)
    abstract fun bindGroceryViewModel(groceryViewModel: GroceryViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProgramViewModel::class)
    abstract fun bindProgramViewModel(programViewModel:ProgramViewModel):ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelProvideFactory):ViewModelProvider.Factory

}