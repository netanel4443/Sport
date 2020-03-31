package com.e.Sport.realmDB.realmObjects

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class ListOfTrainingProgramsRealmObjects:RealmObject() {

    @Required
    @PrimaryKey
    var programName:String=""
    var program=RealmList<ExercisesRealmObject>()

}