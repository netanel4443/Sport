package com.e.Sport.realmDB.repo

import android.util.Log
import com.e.Sport.realmDB.realmObjects.ListOfGroceriesRealmObjects
import com.e.Sport.realmDB.realmObjects.GroceriesRealmObject
import com.e.Sport.ui.recyclerviews.GroceryRecycler.GroceryItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject
import kotlin.collections.HashMap

class GroceryMenusDB @Inject constructor() {

    private var prices=HashMap<String,Pair<Float,Float>>()

    fun getGroceriesMenus(): Observable<HashMap<String,HashMap<String, GroceryItem>>> {
        return Observable.create{emitter->
            val list=HashMap<String,HashMap<String, GroceryItem>>()
            val realm= Realm.getDefaultInstance()

            try {
                realm.executeTransaction {
                    val groceryListObj = it.where(ListOfGroceriesRealmObjects::class.java)
                        .findAll()
                    groceryListObj?.let {
                        it.forEach {
                            val tmpHmap=HashMap<String, GroceryItem>()

                            it.groceryList.forEach {
                                val grocery = GroceryItem()

                                grocery.name = it.groceryName
                                grocery.maxPrice = it.maxPrice
                                grocery.minPrice = it.minPriceList
                                tmpHmap[grocery.name] = grocery
                             }
                            list[it.groceryListName]=tmpHmap

                            val minPrice=it.minPrice
                            val maxPrice=it.maxPrice
                            prices[it.groceryListName]=Pair(minPrice, maxPrice)

                        }
                    }
                }
            }
            catch (e:Exception){ emitter.onError(e)}
            finally {
                realm?.close()
                emitter.onNext(list)
                emitter.onComplete()
            }
        }
    }
    fun getPrices():Observable<HashMap<String,Pair<Float,Float>>>{
        return Observable.just(prices)
    }

    fun deleteFromDB(name:String) {
        Completable.fromAction {
            val realm = Realm.getDefaultInstance()
            try {
                realm.executeTransaction {
                    val groceryListObj = it.where(ListOfGroceriesRealmObjects::class.java)
                        .contains("groceryListName", name)
                        .findFirst()
                    groceryListObj?.deleteFromRealm()
                }
            } catch (e: Exception) {
                Log.e("RealmError", e.printStackTrace().toString())
            } finally {
                realm?.close()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun saveGroceryListToDB(list:HashMap<String,GroceryItem>,
                            prevMenuName:String,newMenuName:String,
                            totalPrice:Pair<Float,Float>) {
        Completable.fromAction {
            val realm = Realm.getDefaultInstance()

            try {
                realm.executeTransaction {
                    var groceryListObj = it.where(ListOfGroceriesRealmObjects::class.java)
                        .contains("groceryListName", prevMenuName)
                        .findFirst()
                    groceryListObj?.deleteFromRealm()

                    groceryListObj =
                        realm.createObject(ListOfGroceriesRealmObjects::class.java, newMenuName)

                    list.values.forEach {
                        val groceryObj = realm.createObject(GroceriesRealmObject::class.java)

                        groceryObj.groceryName = it.name
                        groceryObj.maxPrice = it.maxPrice
                        groceryObj.minPriceList = it.minPrice

                        groceryListObj.groceryList.add(groceryObj)
                    }
                    groceryListObj.minPrice = totalPrice.first
                    groceryListObj.maxPrice = totalPrice.second
                    realm.insertOrUpdate(groceryListObj!!)
                }
            } catch (e: Exception) {
                Log.e("RealmError", e.printStackTrace().toString())
            } finally {
                realm?.close()
            }
        }.subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe()
    }
}