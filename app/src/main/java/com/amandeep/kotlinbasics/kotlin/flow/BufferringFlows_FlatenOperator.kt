package com.amandeep.kotlinbasics.kotlin.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun generateString(id: Int, delay: Long = 500) = flow {
    emit("$id as String-1")
    delay(delay)
    emit("$id as String-2")
}

@OptIn(FlowPreview::class)
fun main(): Unit = runBlocking {
    val time = measureTimeMillis {
        generateFixedFlow()
            .take(5)
            .buffer(5)
            .collect {
                delay(500)
                println(it)
            }
    }
    println("Time to print $time ms")


    generateData(100)
        .take(5)
        .conflate().collect {
            delay(300)
            println("Received After conflate $it")
        }



    generateData(100)
        .take(5)
        .zip(generateData(1000).take(10)) { fast, slow ->
            println("Fast $fast slow $slow")
            fast + slow
        }.collect {
            println("After Zipping $it")
        }


    val mappedFlow: Flow<Flow<String>> = generateData(100)
        .take(5)
        .map { generateString(it) }

    val flow: Flow<String> = mappedFlow.flattenConcat()

    flow.collect {
        println(it)
    }

    generateData(100).take(3).flatMapConcat { generateString(it) }.collect {
        println("FlatMapConcat $it")
    }


}