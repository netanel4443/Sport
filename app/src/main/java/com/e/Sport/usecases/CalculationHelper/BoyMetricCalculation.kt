package com.e.Sport.usecases.CalculationHelper

class BoyMetricCalculation:
    CalculationWay {
    override fun calculte(weight: Int, height: Int, age: Int, method: String): Float {
        return when(method){
            "harris-Benedict"->harrisBenedictMethod(weight, height, age)
            else->mifflinMethod(weight, height, age)
        }
    }

   override fun harrisBenedictMethod(weight: Int, height: Int, age: Int):Float{
        val weight = (13.75 * weight)
        val height = (5.003 * height)
        val age = (6.755 * age)
        return (66.47 + weight + height - age).toFloat()
    }

  override fun mifflinMethod(weight: Int, height: Int, age: Int):Float{
       val wei=10*weight
       val hei=6.25*height
       val ag=5*age
       return (wei+hei-ag+5).toFloat()
    }
}