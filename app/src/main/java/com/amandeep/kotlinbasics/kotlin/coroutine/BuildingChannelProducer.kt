package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    produceChanel().consumeEach {
        print("$it ")
    }
    println("Producer Channel finished")

    printPrimeNumbers(this)


}

fun printPrimeNumbers(coroutineScope: CoroutineScope) {
   val scope =  coroutineScope.launch {
        var cur = numbersFrom(2)
        repeat(10) {
            val prime = cur.receive()
           println(prime)
            cur = filter(cur,prime)
        }
    }

    scope.cancelChildren()

}

fun CoroutineScope.produceChanel(): ReceiveChannel<Int> = produce {
    for (i in 1..5) send(i * i)
}

fun CoroutineScope.numbersFrom(start: Int): ReceiveChannel<Int> = produce {
    var x = start
    while (true) send(x++)
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int): ReceiveChannel<Int> = produce {
    for (num in numbers) if (num % prime != 0) send(num)
}