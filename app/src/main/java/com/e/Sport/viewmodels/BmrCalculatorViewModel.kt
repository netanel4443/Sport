package com.e.Sport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.Sport.usecases.CalculationsUseCase
import javax.inject.Inject


class BmrCalculatorViewModel @Inject constructor(
    private val usecases:CalculationsUseCase):ViewModel() {
    private var measureType=MutableLiveData<Triple<String,String,String>>()
    private var bmr=MutableLiveData<Float>()

    fun calculateBmr():LiveData<Float>{
        return bmr
    }
    fun calculateBmr(sex:String, weight:Int,
                     height:Int, age:Int,type:String){
        if (age*height*weight!=0)
       bmr.value=usecases.calculateBmr(sex, weight, height ,age,type)
    }
    fun switchMeasureType(type:String){
        if (type=="Imperial")
            measureType.value= Triple("Metric","Height(cm)","weight(Kg)")
        else
            measureType.value=Triple("Imperial","Height(inch)","weight(LBs)")

    }
    fun switchMeasureType():LiveData<Triple<String,String,String>>{
        return measureType
    }
}