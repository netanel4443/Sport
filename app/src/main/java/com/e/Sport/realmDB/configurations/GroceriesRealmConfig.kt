package com.e.Sport.realmDB.configurations

import com.e.Sport.realmDB.realmModules.GroceriesRealmModule
import io.realm.RealmConfiguration
import javax.inject.Inject

class GroceriesRealmConfig @Inject constructor(
    private val module: GroceriesRealmModule
){

    fun config():RealmConfiguration{
            return RealmConfiguration.Builder()
                .modules(module)
                .name("grocerylist.realm")
                .build()
    }
}