package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    parentChildRelationShip(coroutineScope = this)
}

/***
 * It will first complete the child then complete the parent job it is called the parent and child relationship
 * */
suspend fun parentChildRelationShip(coroutineScope: CoroutineScope) {
    val job = coroutineScope.launch {
        launch {
            repeat(100) {
                print(".")
                delay(1)
            }
        }
    }


    job.invokeOnCompletion {
        println(" invokeOnCompletion $it ")
    }
    delay(10)
    job.cancelAndJoin()
    println()
    println("finished")
}