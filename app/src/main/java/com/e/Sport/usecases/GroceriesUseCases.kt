package com.e.Sport.usecases

import com.e.Sport.realmDB.repo.GroceryMenusDB
import com.e.Sport.data.GroceryItem
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import kotlin.math.abs

class GroceriesUseCases @Inject constructor(
                  private val repo: GroceryMenusDB) {

     fun totalPriceCalculation(minPrice: Float,maxPrice:Float,totalPrice:Pair<Float,Float>,
                               operatorMin:Int,operatorMax:Int):Pair<Float,Float>{

        val min=(abs(minPrice+(operatorMin*totalPrice.first)))
        val max=(abs(maxPrice+(operatorMax*totalPrice.second)))
        return Pair(min,max)
     }

    fun saveMenu(list:HashMap<String, GroceryItem>, prevMenuName:String, newMenuName:String, totalPrice: Pair<Float, Float>):Completable {
        return repo.saveGroceryListToDB(list,newMenuName,totalPrice)
    }

    fun getGroceryLists(): Observable<HashMap<String,HashMap<String, GroceryItem>>> {
        return repo.getGroceriesMenus()
            .filter{it.isNotEmpty()}

    }
    fun getPrices():Observable<HashMap<String,Pair<Float,Float>>>{
        return repo.getPrices()
    }

    fun updateTotalPriceByItemUpdate(preMinPrice: Float, preMaxPrice: Float,
                         newMinPrice: Float, newMaxPrice: Float,
                         totalPrice: Pair<Float, Float>):Pair<Float,Float> {
        var minOperator=1
        var maxOperator=1

        if ((preMinPrice-newMinPrice)>0)
            minOperator=-1
        if ((preMaxPrice-newMaxPrice)>0)
            maxOperator=-1

        val totalMin:Float = abs(preMinPrice-newMinPrice)
        val totalMax:Float = abs(preMaxPrice-newMaxPrice)

       return totalPriceCalculation(totalMin,totalMax,totalPrice,minOperator,maxOperator )
    }

    fun deleteMenu(name: String):Completable {
        return repo.deleteFromDB(name)
    }


}