package com.amandeep.kotlinbasics.kotlin.variable


fun main() {

    // Var is a keywords use to declare variable which values can be changes or reassigned.

    var a = "Kotlin"
    println("Hello $a") //o/p -> Hello Kotlin
    a = "is a Language" // work
    println("Kotlin $a") // o/p -> Kotlin is a Language

 //    ==========================================
    // Val is a keywords use to declare variable which values can not be changes or reassigned.
    // 1. We can assign function to val
    // 2. we can assign class to val

    val b = "Kotlin"
    println("Hello $b") //o/p -> Hello Kotlin

 //   b = "is a Language" // compile tim error , coz Val can not be reassigned

    val c = ValTest()

    val d= assignValueToValByFunction()

    println(d) // o/p-> Val Assign at Run-Time




}
// ========================================================
/*
    CONST -> We can not re-assign the values once assign , it's similar to val but the only difference it this one is
    compile-time constant. we can not assign values by functions or class.
    1. it is used to declared the properties which are immutable (read only)
    2. You can not assign a const variable to a function or to some class.
    3. const should be top of file or using inside the companion object.

     companion object {
        const val FILE_EXTENSION = ".mp3"
        val FILENAME: String
        get() = "Audio_" +file_name + FILE_EXTENSION
    }
     Note ->  Only primitives and String are allowed.
 */


const val name="Kotlin" // work

// const val function= assignValueToValByFunction() // ot work

// const val classAssign=ValTest() // Not work.

// this will assign to keyword val that means we can assign values to val at run-time. but it cant not reassigned the values.
fun assignValueToValByFunction(): String {
    return "Val Assign at Run-Time"
}

class ValTest{

    init {
        println("ValTest class initialized")
    }
}



