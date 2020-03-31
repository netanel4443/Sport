package com.e.Sport.di.modules

import android.app.Application
import com.e.Sport.realmDB.realmModules.GroceriesRealmModule
import com.e.Sport.realmDB.realmModules.TrainingProgramRealmModule
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Named

@Module
object AppModule {

    @Provides
    @JvmStatic
    fun provideApplicatioContext(app:Application)=app.applicationContext

}