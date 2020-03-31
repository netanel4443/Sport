package com.e.Sport.realmDB.configurations

import com.e.Sport.di.anotations.AppScope
import com.e.Sport.realmDB.realmModules.TrainingProgramRealmModule
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingProgramsRealmConfig @Inject constructor(
       private val  module: TrainingProgramRealmModule
){

    fun config():RealmConfiguration{
        return RealmConfiguration.Builder()
            .modules(module)
            .name("training_program.realm")
            .build()
    }
}