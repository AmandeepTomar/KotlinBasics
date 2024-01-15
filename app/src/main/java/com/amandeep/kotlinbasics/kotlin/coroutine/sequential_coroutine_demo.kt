package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main Start ${Thread.currentThread().name}")
    val time = measureTimeMillis {
        val m1 = returnMessageOne()
        val m2 = returnMessageTwo()
        print("Result $m1 and $m2")
    }
    println("measureTime in $time ms")
    print("End Start ${Thread.currentThread().name}")
}

suspend fun returnMessageOne() : String{
    delay(1000)
    println("returnMessageOne")
    return "MessageOne"
}

suspend fun returnMessageTwo() : String{
    delay(1000)
    println("returnMessageTwo")
    return "MessageTwo"
}