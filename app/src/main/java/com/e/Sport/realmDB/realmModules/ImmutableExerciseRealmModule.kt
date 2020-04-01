package com.e.Sport.realmDB.realmModules

import com.e.Sport.realmDB.realmObjects.ExerciseNameRealmObject
import io.realm.annotations.RealmModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RealmModule(classes = [ExerciseNameRealmObject::class])
class ImmutableExerciseRealmModule @Inject constructor()