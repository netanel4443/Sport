package com.e.Sport.usecases.CalculationHelper

class Male: CalculationType {
    override fun type(type:String): CalculationWay {
        return when(type){
             "Imperial"-> BoyImperialCalculation()
             else -> BoyMetricCalculation()
        }
    }
}