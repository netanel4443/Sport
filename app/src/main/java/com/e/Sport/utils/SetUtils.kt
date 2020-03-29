package com.e.Sport.utils

import kotlin.collections.HashSet

fun <E>HashSet<E>.replaceWith( elements:Collection<E>){
    clear()
    addAll(elements)
}