package com.e.Sport.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides @JvmStatic
    fun provideApplicatioContext(app:Application)=app.applicationContext

}