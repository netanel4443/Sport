package com.e.Sport.di.components

import android.app.Application
import com.e.Sport.di.BaseApplication
import com.e.Sport.di.modules.AppModule
import com.e.Sport.di.screens.ActivitiesBuilder
import com.e.Sport.di.viewmodelsModule.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                      AppModule::class,
                      ActivitiesBuilder::class,
                      ViewModelModule::class])
interface AppComponent:AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
     @BindsInstance
     fun application(app:Application): Builder

     fun build():AppComponent

    }
}