package com.e.Sport.realmDB.realmObjects

import io.realm.RealmObject

open class ExercisesRealmObject:RealmObject() {

    var exerciseName:String=""
    var sets:Int=0
    var reps: Int=0

}