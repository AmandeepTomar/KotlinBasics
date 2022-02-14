package com.amandeep.kotlinbasics.kotlin.immutableinjava

/**
 * 1. Make class final so that it can not be a sub class
 * 2. make all properties final and private
 * 3. Must have only setters
 * */

/**
 * Advantages of Immutable
 * Data integrity in Multi-Threaded environment
 * Used as Constant
 * Used as key in hashMap
 * */

object JavaMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val obj = ImmutableClassInJava("Kotlin", "JVM", 16)
        println(obj.toString()) // this class in immutable as we can not have any setter


        val address = Address("Noida", "Sector-135")
        val objNew = ImmutableClassWithMutableClass("Amandeep", "30", address)
        println(objNew.toString())
        // o/p ImmutableClassWithMutableClass{name='Amandeep', age='30', address=Address{city='Noida', street='Sector-135'}}
        val newAddress = objNew.address
        newAddress.city = "Gurugram"
        newAddress.street = "DLF-Phase IV"

        println("After Update Address $objNew")
    // o/p After Update Address ImmutableClassWithMutableClass{name='Amandeep', age='30', address=Address{city='Gurugram', street='DLF-Phase IV'}}
   // Here address is changed that break the immutability property to fix this we need to return the copy of Address as it is not
       // immutable object

        // After return the new object it will fix the immutability

    }
}