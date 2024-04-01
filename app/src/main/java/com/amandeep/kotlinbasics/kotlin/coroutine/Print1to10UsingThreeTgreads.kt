package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

var currentNo = 1
fun main(): Unit = runBlocking {

   val job = launch() {
      // println("here 1")
        printNo(3,1,"ThreadName-1")
    }
    val job1 = launch(Dispatchers.IO) {
       // println("here 2")
         printNo(3,2,"ThreadName-2")
     }

    val job2 = launch(Dispatchers.Default) {
       // println("here 3")
        printNo(3,0,"ThreadName-0")
    }

    job.join()
    job1.join()
    job2.join()
}

suspend fun  printNo(threadCount:Int,threadNo:Int,threadName:String) {
   // println("Here $threadCount -- $threadNo and ${currentNo<=10}")
    while (currentNo<=20) {
        if (currentNo % threadCount == threadNo) {
            println("cureent No $currentNo ${threadName}")
            currentNo++
        }
        delay(100)
    }
}
