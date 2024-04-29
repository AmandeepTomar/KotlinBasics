package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun <T, R> ReceiveChannel<T>.map(
    coroutineScope: CoroutineScope,
    transform: (T) -> R
): ReceiveChannel<R> {
    val receiveChannel = this
    return coroutineScope.produce {
        try {
            for (value in receiveChannel) send(transform(value))
        } catch (e: CancellationException) {
            receiveChannel.cancel()
        }
    }
}

fun <T, R> map(
    coroutineScope: CoroutineScope,
    producer: ReceiveChannel<T>,
    transform: (T) -> R
): ReceiveChannel<R> = coroutineScope.produce {
    for (value in producer) send(transform(value))
}

@OptIn(ExperimentalCoroutinesApi::class)
fun createPipelineOfNumbers(coroutineScope: CoroutineScope, start: Int): ReceiveChannel<Int> =
    coroutineScope.produce {
        var x = start
        while (true) {
            send(x++)
            delay(500)
        }
    }

fun consumePileLineData(coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) =
    coroutineScope.launch {
        producer.consumeEach {
            println(it)
        }
    }

fun main() = runBlocking {
    val numbers = createPipelineOfNumbers(this, 1).map(this) {
        it * it * it
    }
    // consumePileLineData(this, numbers)

//    val squareProducer = map(this, numbers) {
//        it * it
//    }
//    consumePileLineData(this, squareProducer)
    consumePileLineData(this, numbers)
    delay(5000)
    numbers.cancel()
}