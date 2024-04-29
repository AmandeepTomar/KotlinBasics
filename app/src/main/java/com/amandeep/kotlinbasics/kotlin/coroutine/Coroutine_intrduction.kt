package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        waitTillCoroutineFinishedUsingJoin()
        jobIsNotJoined()
        // if we use delay(4000) the Job Finished in jobIsNotJoined will print coz ,
        // main will be delayed to finish
    }

}

suspend fun coroutineLaunchBuilder(){

}
suspend fun jobIsNotJoined() {
    val job = CoroutineScope(Dispatchers.IO).launch {
        delay(3000)
        println("Job Finished in jobIsNotJoined") // this will not called as it is exit the main before reach here.
    }

    println("Main thread executing....jobIsNotJoined") // 1

    println("MainThread finished..jobIsNotJoined") // 2
}

suspend fun waitTillCoroutineFinishedUsingJoin() {

    val job = GlobalScope.launch {
        delay(5000) // delayed for 10 sec
        println("Job1 finished") // 2 execute after 10 sec
    }
    println("Main thread executing...") //1

    // wait till job1 finised or joined,.main is not executed
    runBlocking {
        job.join()
        println("Coroutine joined") // 3
    }
    println("Main thread Finished...") // 4
}