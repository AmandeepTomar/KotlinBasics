package com.amandeep.kotlinbasics.kotlin.basics

fun main() {

// For Loops in kotlin
   // The for loop iterates through anything that provides an iterator. This is equivalent to the foreach loop in languages like C#.

    val list= listOf(1,2,3,4,5,6,7,8,9)

    // without body
    for (item in list)
        print("$item ")


    println("\n=======")
    // with body
    for (item:Int in list){
        print("$item ")
    }
    println("\n========")

  //  To iterate over a range of numbers, use a range expression:
    for (i in 1..10){
        print("$i ")
    }
    println("\n========")

    for(i in 6 downTo 0){
        print("$i ")
    }
    println("\n========")

    for(i in 6 downTo 0 step 2){
        print("$i ")
    }
    println("\n========")

    /**
     * A for loop over a range or an array is compiled to an index-based loop that does not create an iterator object.

    If you want to iterate through an array or a list with an index, you can do it this way:
     * */

    for (i in list.indices){
        println("${list[i]} and index $i")
    }
    println("\n========")
    for ((index,value) in list.withIndex()){
        println("Index is $index and Values $value")
    }


}