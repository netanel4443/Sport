package com.e.Sport.usecases.CalculationHelper

class Female: CalculationType {
    override fun type(type:String): CalculationWay {
        return when(type){
            "Imperial"->GirlImperialCalculation()
            else ->GirlMetricCalculation()
        }
    }
}