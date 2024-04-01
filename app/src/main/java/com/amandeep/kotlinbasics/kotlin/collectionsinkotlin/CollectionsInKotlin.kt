package com.amandeep.kotlinbasics.kotlin.collectionsinkotlin

fun main() {

    val set =  mutableSetOf<String>("a","a","b")
    println(set)

    val map = mutableMapOf("a" to 95,"b" to 96)
    println(map)

    for (item in map){
        println("${item.key} and ${item.value}")
    }
}