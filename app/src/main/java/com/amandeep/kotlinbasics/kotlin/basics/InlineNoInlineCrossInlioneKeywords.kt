package com.amandeep.kotlinbasics.kotlin.basics

import com.amandeep.kotlinbasics.kotlin.genericsinkotlin.User




fun main() {

    printMessage {
        println("Messagge")
        return@printMessage
    }
    printMessage { print("SecondMessage") }


}


inline fun <reified T> filterList(list: List<Video>): List<T> {
    return list.filterIsInstance<T>()
}

inline fun printMessage(crossinline a: () -> Unit) {
    a.invoke()
}


inline fun applyTransformation(
    videos: List<Video>,
    noinline transformation: (Video) -> Video, // do not want this fun as inline
    crossinline onComplete: (List<Video>) -> Unit // prevent non local return
) {
    onComplete(videos.map(transformation))
}

sealed class Video() {
    data class Sports(val name: String) : Video()
    data class Entertainment(val name: String) : Video()
    data class Movies(val name: String) : Video()
}

