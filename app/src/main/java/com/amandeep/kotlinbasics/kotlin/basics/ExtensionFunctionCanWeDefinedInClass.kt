package com.amandeep.kotlinbasics.kotlin.basics


fun main() {

    val sampleExtension = SampleExtension()

    // it is not callable.
    //     sampleExtension.toGetValue()
}

class SampleExtension {

    fun SampleExtension.toGetValue() = "return Value "

}