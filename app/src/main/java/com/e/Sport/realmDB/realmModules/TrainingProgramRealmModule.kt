package com.e.Sport.realmDB.realmModules

import com.e.Sport.realmDB.realmObjects.ExercisesRealmObject
import com.e.Sport.realmDB.realmObjects.ListOfTrainingProgramsRealmObjects
import com.e.Sport.realmDB.repo.TrainingProgramsDB
import io.realm.annotations.RealmModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RealmModule(classes = [ListOfTrainingProgramsRealmObjects::class,
                        ExercisesRealmObject::class])
class TrainingProgramRealmModule @Inject constructor()