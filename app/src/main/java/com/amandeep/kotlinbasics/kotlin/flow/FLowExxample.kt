package com.amandeep.kotlinbasics.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun generateData(delay:Long=1000) = flow<Int> {
    var num = 1
    while (true) {
        delay(delay)
        emit(num++)
    }
}.flowOn(Dispatchers.IO)

fun generateFixedFlow(item:Int=5) = flow<Int> {
    for (i in 1..item) {
        delay(100)
        emit(i)
    }
}

fun main(): Unit = runBlocking {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    val job = coroutineScope.launch {
        generateData().collect {
            println(it)
        }
    }
    delay(5000)
    job.cancel()
    println("Start collection new")

    launch {
        generateFixedFlow().collect {
            println("collect from 1 $it")
        }
    }

    delay(300)
    launch {
        generateFixedFlow().collect {
            println("collect from 2 $it")
        }
    }

    delay(600)
    launch {
        generateFixedFlow().collect {
            println("collect from 3 $it")
        }
    }

    launch {
        generateFixedFlow()
            .map { "after map ${it * it}" }
            .filter { it == "after map 16" }.collect {
                println(it)
            }
    }

    launch {
        val result = generateFixedFlow().reduce { accumulator, value -> accumulator + value }
        val resultMul = generateFixedFlow().reduce { accumulator, value -> accumulator * value }
        println(result)
        println(resultMul)
    }

    launch {
        val result = generateFixedFlow().fold(10) { acc, value ->
            acc + value
        }
        println("Fold result $result")
    }

}