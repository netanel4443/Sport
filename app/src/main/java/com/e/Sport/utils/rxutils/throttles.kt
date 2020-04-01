package com.e.Sport.utils.rxutils


import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun <T> rx.Observable<T>.throttleFirstClick():rx.Observable<T> =
    throttleFirst(1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())

