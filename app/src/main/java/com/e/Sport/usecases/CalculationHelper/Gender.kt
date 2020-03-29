package com.e.Sport.usecases.CalculationHelper

class Gender {

    fun boyOrGirl(sex:String): CalculationType {
       return when (sex){
            "male"-> Male()
            else-> Female()
        }
    }
}