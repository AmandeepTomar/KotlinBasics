package com.amandeep.kotlinbasics.kotlin.basics


fun main() {
arraysInKotlin()
}

private fun arraysInKotlin(){

    // int Array
    val intArray= intArrayOf(1,2,3,4,5,6)
    println(intArray.contentToString())

    // Any Type Array it can hold any type
    val anyArray= arrayOf("One","Two","Three",1,2,1.0,2.0,3f,Item("Name",1))
    println(anyArray.contentToString())

    // Double Array
    val doubleArray= doubleArrayOf(1.0,2.2,3.4,4.4,5.5,6.6)
    println(doubleArray.contentToString())

    // print array with indicies

    for (index in anyArray.indices){
        print("item at indices${index} and item ${anyArray[index]}")
    }

    // print areray with items

    val array : Array<Item> = arrayOf<Item>(Item("one",1),Item("two",2),Item("Three",3))

    for(item in array){
        println(item)
    }

}


data class Item(val name:String,val count:Int)