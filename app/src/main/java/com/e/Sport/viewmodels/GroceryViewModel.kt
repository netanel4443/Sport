package com.e.Sport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.Sport.data.GroceryItem
import com.e.Sport.usecases.GroceriesUseCases
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GroceryViewModel @Inject constructor(
                private val useCases:GroceriesUseCases) : ViewModel() {

        private var groceryItem: GroceryItem = GroceryItem()
        private var groceriesMenu=MutableLiveData(HashMap<String, GroceryItem>())
        private var totalPrice=MutableLiveData(Pair(0f,0f))
        private val compositeDisposable=CompositeDisposable()
        private var groceriesMenus=MutableLiveData(HashMap<String,HashMap<String, GroceryItem>>())
        private var prices=HashMap<String,Pair<Float,Float>>()
        private var menuName=MutableLiveData<String>()
        private var progressBar=MutableLiveData("hide")

        fun addItem(item: GroceryItem, list:HashMap<String, GroceryItem>){
            groceryItem=item
            list[item.name]=item
            groceriesMenu.value=list
            val pair= useCases.totalPriceCalculation(item.minPrice,item.maxPrice,totalPrice.value!!,1,1)
            totalPrice.value=pair
        }

        fun getGroceryList():LiveData<HashMap<String, GroceryItem>>{
            return groceriesMenu
        }

        fun getGroceryListClicked(name:String){
            groceriesMenu.value= groceriesMenus.value?.get(name)
            menuName.value=name
            totalPrice.value=prices[name]
        }

        fun getGroceriesMenus():LiveData<HashMap<String,HashMap<String, GroceryItem>>>{
            return groceriesMenus
        }

        fun changeProgressBarState(state:String){
            progressBar.value=state
        }

        fun progressBarState():LiveData<String>{
            return progressBar
        }



        fun deleteGroceryItem(item: String,list:HashMap<String, GroceryItem>) {
            val minPrice=list[item]!!.minPrice
            val maxPrice=list[item]!!.maxPrice
            val pair= useCases.totalPriceCalculation(minPrice,maxPrice,totalPrice.value!!,-1,-1)
            list.remove(item)
            totalPrice.value=pair
        }
        fun deleteMenu(name: String){
          compositeDisposable.add(useCases.deleteMenu(name).subscribe({},{}))
            prices.remove(name)
            val tmpHmap=groceriesMenus.value
            tmpHmap?.remove(name)
            groceriesMenus.value=tmpHmap
        }

        fun totalPrice():LiveData<Pair<Float,Float>>{
            return totalPrice
        }

        fun saveMenu(list:HashMap<String, GroceryItem>, prevMenuName:String, newMenuName:String) {
            compositeDisposable.add(
            useCases.deleteMenu(prevMenuName)
            .doOnSubscribe { progressBar.value="show" }
            .concatWith {useCases.saveMenu(list,prevMenuName,newMenuName,totalPrice.value!!)
                .doOnSubscribe { compositeDisposable.add(it) }
                .doOnComplete { val tmpMap= groceriesMenus.value
                    println("save ${prevMenuName} ${newMenuName}")
                    tmpMap?.remove(prevMenuName)
                    tmpMap?.put(newMenuName,list)
                    prices.remove(prevMenuName)
                    prices[newMenuName]=totalPrice.value!!
                    groceriesMenus.value=tmpMap
                    println("save2 ${groceriesMenus.value?.size}")
                }
                .doOnTerminate { progressBar.value="hide" }
                .subscribe({},{it.printStackTrace()})
            }.subscribe ( {},{it.printStackTrace()
                               progressBar.value="hide" } ))


        }

        fun getMenus(){
            compositeDisposable.add( useCases.getGroceryLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete{useCases.getPrices().subscribe{prices=it } }
                .doOnError { it.printStackTrace() }
                .subscribe{
                    groceriesMenus.value=it
                })
        }
        fun updateTotalPriceByItemUpdate(preMinPrice:Float,preMaxPrice:Float,
                                         newMinPrice:Float,newMaxPrice:Float) {
            totalPrice.value=  useCases.updateTotalPriceByItemUpdate(preMinPrice,preMaxPrice, newMinPrice,
                newMaxPrice,totalPrice.value!!)
        }

        fun getMenuName(): LiveData<String> {
            return menuName
        }

        fun setMenuName(name: String) {
            menuName.value=name
        }

        override fun onCleared() {
            super.onCleared()
            compositeDisposable.dispose()
            println("cleared")
        }

}
