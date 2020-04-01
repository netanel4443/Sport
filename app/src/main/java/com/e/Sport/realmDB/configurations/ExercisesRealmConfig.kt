package com.e.Sport.realmDB.configurations

import com.e.Sport.di.anotations.AppScope
import com.e.Sport.realmDB.realmModules.ImmutableExerciseRealmModule
import com.e.Sport.realmDB.realmModules.TrainingProgramRealmModule
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRealmConfig @Inject constructor(
       private val  module: ImmutableExerciseRealmModule
){

    fun config():RealmConfiguration{
        return RealmConfiguration.Builder()
            .assetFile("exercises.realm")
            .modules(module)
            .build()
    }
}