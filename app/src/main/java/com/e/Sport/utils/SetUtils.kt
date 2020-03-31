package com.e.Sport.utils

import kotlin.collections.HashSet

fun <E>HashSet<E>.replaceAll(elements:Collection<E>){
    clear()
    addAll(elements)
}

fun <E>HashSet<E>.replace(prevElement: E,newElement:E){
    remove(prevElement)
    add(newElement)
}