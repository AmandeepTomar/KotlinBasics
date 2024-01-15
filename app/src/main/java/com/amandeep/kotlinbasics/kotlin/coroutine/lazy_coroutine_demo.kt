package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    println("Main Start ${Thread.currentThread().name}")
        val m1 = async(start = CoroutineStart.LAZY) { returnMessageOne()}
        val m2 = async(start = CoroutineStart.LAZY) {  returnMessageTwo()}
        println("Result ${m1.await()} and ${m2.await()}")
    print("End Start ${Thread.currentThread().name}")

}