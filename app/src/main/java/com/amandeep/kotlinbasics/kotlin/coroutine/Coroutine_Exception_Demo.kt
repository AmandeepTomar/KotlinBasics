package com.amandeep.kotlinbasics.kotlin.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


fun main() {

    GlobalScope.launch {
        launch {
            launch {
                throw Exception("")
            }
        }
    }
}