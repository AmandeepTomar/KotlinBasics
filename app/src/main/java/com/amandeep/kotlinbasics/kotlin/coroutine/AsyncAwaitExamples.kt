package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
   val time = measureTimeMillis {
       val one = async { getMessageOne() }.await()
       val two = async { getMessageTwo(one) }.await()
       println(two)
   }

    println("Time taklen is $time")


}


private suspend fun getMessageOne(): String {
    delay(1000)
    val message = " Hi "
    return message
}

private suspend fun getMessageTwo(message:String): String {
    delay(1500)

    return   "${message}, We are getting value from getMessageOne"
}

