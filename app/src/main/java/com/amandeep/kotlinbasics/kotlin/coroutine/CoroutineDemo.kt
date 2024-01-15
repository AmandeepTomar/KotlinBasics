package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val scope = CoroutineScope(Job())
fun main() {

    runBlocking { // this on execute on Main thread
        scope.launch {// create new thread T1
            executeTask()
        }
        delay(200) // on main thread.
    }


    runBlocking {

        // this  coroutineScope
      //   this.coroutineContext CoroutineContext instance
    }

}

suspend fun executeTask(){
    doSomeLongRunningTask()
}

fun doSomeLongRunningTask() {
    print("Hello ${Thread.currentThread().name}")
}
