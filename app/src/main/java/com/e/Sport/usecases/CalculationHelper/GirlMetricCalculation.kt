package com.e.Sport.usecases.CalculationHelper

class GirlMetricCalculation:
    CalculationWay {
    override fun calculte(weight: Int, height: Int, age: Int, method: String): Float {
      return when(method){
        "harris-Benedict"->harrisBenedictMethod(weight, height, age)
         else->mifflinMethod(weight, height, age)
      }
    }

    override fun harrisBenedictMethod(weight: Int, height: Int, age: Int):Float{
        val wei = (9.563  * weight)
        val hei = (1.85  * height)
        val ag = ( 4.676 * age)
       return (655.1  + wei + hei - ag).toFloat()
    }

    override fun mifflinMethod(weight: Int, height: Int, age: Int):Float {
        val wei=10*weight
        val hei=6.25*height
        val ag=5*age
        return (wei+hei-ag-161).toFloat()
    }
}