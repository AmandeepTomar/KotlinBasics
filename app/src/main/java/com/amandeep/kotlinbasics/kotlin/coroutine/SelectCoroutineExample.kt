package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select


fun CoroutineScope.produceDelaySend() = produce<Int> {
    for (num in 1..10) {
        delay(100)
        send(num)
    }
    println("Done Sending")
}

fun CoroutineScope.produceSelectSend(channel: SendChannel<Int>) = produce<Int> {
    for (i in 1..10) {
        delay(100)
        select<Unit> {
            onSend(i) {}
            channel.onSend(i) {}
        }
    }
}

fun CoroutineScope.producer1WithoutDelay() = produce<String> {
    var start = 100
    send("from producer1 ${start++}")
}

fun CoroutineScope.producer2WithoutDelay() = produce<String> {
    var start = 200
    send("from producer2 ${start++}")

}


fun CoroutineScope.producer1() = produce<String> {
    var start = 100
    while (true) {
        delay(200)
        send("from producer1 ${start++}")
    }
}

fun CoroutineScope.producerWithDelay(delay: Long) = produce<String> {
    var start = 100
    while (true) {
        delay(delay)
        send("producerWithDelay ${start++}")
    }
}

fun CoroutineScope.producer2() = produce<String> {
    var start = 200
    while (true) {
        delay(400)
        send("from producer2 ${start++}")
    }
}


fun selectExample(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val producer1 = producer1()
        val producer2 = producer2()

        repeat(10) {
            select<Unit> {
                producer1.onReceive {
                    println(it)
                }
                producer2.onReceive {
                    println(it)
                }
            }
        }
    }
}

fun main() = runBlocking {
    //selectExample(this)

    //selectExampleWithCancel(this)

    // delayConsumeDemoDemo(this)

    // selectSendDelayConsume(this)
    producerWithTimeout(this)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun producerWithTimeout(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val p1 = producerWithDelay(50)
        select {
            p1.onReceive {
                println("Received $it")
            }

//            onTimeout(1000){
//                println("Time out")
//            }
        }
    }
}

fun selectSendDelayConsume(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val channel = Channel<Int>()
        val producer = produceSelectSend(channel)
        launch {
            channel.consumeEach {
                println("Side channel $it")
            }
        }

//        launch {
        producer.consumeEach {
            delay(500)
            println(it)
        }
//        }

    }
}


fun delayConsumeDemoDemo(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val p1 = produceDelaySend()
        p1.consumeEach {
            delay(500)
            print(it)
        }
    }
    // o/p
    // 123456789Done Sending
    //10
}

fun selectExampleWithCancel(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val p2 = producer2WithoutDelay()
        val p1 = producer1WithoutDelay()
        repeat(10) {
            val result = select<String> {
                p1.onReceiveCatching {
                    it.getOrNull() ?: "P1 is Close"
                }
                p2.onReceiveCatching {
                    it.getOrNull() ?: "P2 is Cclose"
                }
            }
            println(result)
        }

        println("Finish Main")
        p1.cancel()
        p2.cancel()
    }

}
