package com.e.Sport.realmDB.realmObjects

import io.realm.RealmObject

open class GroceriesRealmObject:RealmObject() {

    var groceryName:String=""
    var minPriceList:Float=0f
    var maxPrice: Float=0f
}