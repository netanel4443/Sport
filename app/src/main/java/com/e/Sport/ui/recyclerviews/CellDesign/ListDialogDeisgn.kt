package com.e.Sport.ui.recyclerviews.CellDesign

import android.graphics.Color
import android.graphics.Typeface

object  ListDialogDeisgn {
    val designMap= HashMap<Boolean,Pair<Int,Int>>()

    init {
        designMap[true]=Pair(Color.BLUE,Typeface.BOLD)
        designMap[false]= Pair(Color.BLACK,Typeface.NORMAL)
    }

}