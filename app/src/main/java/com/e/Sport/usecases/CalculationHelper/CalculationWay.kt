package com.e.Sport.usecases.CalculationHelper

interface CalculationWay {
    fun calculte(weight:Int, height:Int, age:Int,method:String):Float
    fun mifflinMethod(weight:Int, height:Int, age:Int):Float
    fun harrisBenedictMethod(weight:Int, height:Int, age:Int):Float
}