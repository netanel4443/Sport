package com.e.Sport.usecases

import com.e.Sport.usecases.CalculationHelper.Gender
import javax.inject.Inject

class CalculationsUseCase @Inject constructor() {

    fun calculateBmr(sex:String, weight:Int,
                     height:Int, age:Int,type:String):Float{

       return Gender()
          .boyOrGirl(sex)
          .type(type)
          .calculte(weight,height,age, "test")
    }

}