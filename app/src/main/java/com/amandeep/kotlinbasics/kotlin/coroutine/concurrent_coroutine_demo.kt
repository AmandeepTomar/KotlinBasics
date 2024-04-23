package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main Start ${Thread.currentThread().name}")
    val time = measureTimeMillis {
        val m1 = async { returnMessageOne() }
        val m2 = async { returnMessageTwo() }
        print("Result ${m1.await()} and ${m2.await() }")
    }
    println("measureTime in $time ms")
    print("End Start ${Thread.currentThread().name}")
}

// o/p  Main Start main
//returnMessageOne
//returnMessageTwo
//Result MessageOne and MessageTwomeasureTime in 1025 ms
//End Start main

