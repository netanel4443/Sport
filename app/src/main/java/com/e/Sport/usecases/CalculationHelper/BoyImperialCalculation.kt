package com.e.Sport.usecases.CalculationHelper

class BoyImperialCalculation:
    CalculationWay {
    override fun calculte(weight: Int, height: Int, age: Int, method: String): Float {
        return when(method){
            "harris-Benedict"->harrisBenedictMethod(weight, height, age)
            else->mifflinMethod(weight, height, age)
        }
    }

   override fun harrisBenedictMethod(weight: Int, height: Int, age: Int):Float{
        val wei = (6.24 * weight)
        val hei = (12.7 * height)
        val ag = (6.755 * age)
        return (66.47 + wei + hei - ag).toFloat()
    }
    override fun mifflinMethod(weight: Int, height: Int, age: Int):Float{
        val wei=4.536 *weight
        val hei=15.88 *height
        val ag=5*age
        return (wei+hei-ag+5).toFloat()
    }
}