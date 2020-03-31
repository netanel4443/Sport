package com.e.Sport.realmDB.realmModules

import com.e.Sport.realmDB.realmObjects.GroceriesRealmObject
import com.e.Sport.realmDB.realmObjects.ListOfGroceriesRealmObjects
import io.realm.annotations.RealmModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RealmModule(classes = [GroceriesRealmObject::class,
    ListOfGroceriesRealmObjects::class])
class GroceriesRealmModule @Inject constructor()