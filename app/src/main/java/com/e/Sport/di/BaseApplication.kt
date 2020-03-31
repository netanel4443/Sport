package com.e.Sport.di

import com.e.Sport.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration




class BaseApplication:DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val groceriesRealmConfig = RealmConfiguration.Builder()
            .name("grocerylist.realm")
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(groceriesRealmConfig)

        val sportRealmConfig =
            RealmConfiguration.Builder()
            .name("training_program.realm")
            .build()
    }
}