package com.amandeep.kotlinbasics.kotlin.flow

import com.amandeep.kotlinbasics.kotlin.flow.generateFixedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    val sharedFlow = generateFixedFlow(30).shareIn(this, SharingStarted.WhileSubscribed(),20)
    launch {
        sharedFlow.collect {
            println("SharedFlow1 $it")
        }

    }

    delay(2000)
    launch {
        sharedFlow.collect {
            println("SharedFlow2 $it")
        }
    }




}