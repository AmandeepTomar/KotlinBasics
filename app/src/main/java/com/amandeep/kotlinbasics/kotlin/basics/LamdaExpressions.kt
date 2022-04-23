package com.amandeep.kotlinbasics.kotlin.basics

fun main() {
    addTwoNo(5, 10)
    // lamda function
    println(sum(5,10))
    add(10,5)
}

// Normal Function
fun addTwoNo(a: Int,b: Int){
    val add= a+b;
    print(add)
}

// lamda function
val sum :(Int,Int)->Int={a:Int,b:Int->a+b}
//lamda function
val add = {a:Int,b:Int-> print(a+b)}