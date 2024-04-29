package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    typeOfChannelInitialization(this)
    channelExample1(this)
    channelExample2(this)
    channelCloseAndIteration(this)

}

fun channelCloseAndIteration(coroutineScope: CoroutineScope) {
    println("channelCloseAndIteration")
    coroutineScope.launch {
        val channel = Channel<Int>()
        launch {
            for (i in 1..5) channel.send(i*i)
            channel.close()
        }

        for (receiver in channel){
            println(receiver)
        }
    }
}

fun channelExample2(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val channel = Channel<Int>()
        launch {
            for (i in 1..5){
                channel.send(i*i)
            }
        }
        repeat(5){
            println(channel.receive())
        }
    }
}

fun typeOfChannelInitialization(coroutineScope: CoroutineScope) {
    coroutineScope.launch{
        val unlimitedChannel = Channel<String>(Channel.UNLIMITED)
        val conflatedChannel = Channel<String>(Channel.CONFLATED)
        val bufferedChannel = Channel<String>(10)
        val rendezvousChannel = Channel<String>()
    }
}

suspend fun channelExample1(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val channel = Channel<String>() // rendezvous Channel

        launch {
            channel.send("A1")
            channel.send("A2")
            log("A done")
        }

        launch {
            channel.send("B1")
            log("B done")
        }

        launch {
            repeat(3) {
                val x = channel.receive()
                log(x)
            }
        }
    }.join()
    println()
}

fun log(message: Any?) {
    println("${Thread.currentThread().name} and $message")
}