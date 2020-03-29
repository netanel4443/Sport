package com.e.Sport.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun View.chooseAnimation(context: Context, animation:Int){
    val chosenAnimation=AnimationUtils.loadAnimation(context,animation)
    startAnimation(chosenAnimation)
}