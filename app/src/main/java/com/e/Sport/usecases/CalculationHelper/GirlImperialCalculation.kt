package com.e.Sport.usecases.CalculationHelper

class GirlImperialCalculation:
    CalculationWay {
    override fun calculte(weight: Int, height: Int, age: Int, method: String): Float {
        return when(method){
            "harris-Benedict"->harrisBenedictMethod(weight, height, age)
            else->mifflinMethod(weight, height, age)
        }
    }

    override fun harrisBenedictMethod(weight: Int, height: Int, age: Int):Float{
        val wei = (4.35   * weight)
        val hei = (4.7  * height)
        val ag = ( 4.7  * age)
        return (655.1  + wei + hei - ag).toFloat()
    }
    override fun mifflinMethod(weight: Int, height: Int, age: Int):Float{
        val wei=4.536 *weight
        val hei=15.88 *height
        val ag=5*age
        return (wei+hei-ag-161).toFloat()
    }
}