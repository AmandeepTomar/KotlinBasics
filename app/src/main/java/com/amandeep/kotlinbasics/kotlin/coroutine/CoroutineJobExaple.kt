package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    try {
        val job = launch {
           // println("isActive $isActive")
            println("ensureActive ${ensureActive()}")
            delay(1000)
            println("After Delay ensureActive ${ensureActive()}")

        }
        delay(100)
        job.cancel()
        job.join()
    }catch (e:CancellationException){
        println("Exception $e")
    }

}