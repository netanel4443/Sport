package com.e.Sport.realmDB.realmObjects

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class ListOfGroceriesRealmObjects:RealmObject() {
    @Required
    @PrimaryKey
    var groceryListName:String=""
    var minPrice:Float=0f
    var maxPrice:Float=0f
    var groceryList=RealmList<GroceriesRealmObject>()

}