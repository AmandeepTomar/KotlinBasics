package com.amandeep.kotlinbasics.kotlin.string

fun main() {

    /**
     * String is immutable class in java
     * If we create any string using literals " " i.e store in string constant pool
     * Once a string created that can not be modified or changed ,
     * you can create new string but can not changed a string that created.
     * Like
     * String str="Kotlin"
     * String str2=str.concat("is a language" ) this is not modified the str , it create new string str2.
     *
     * */

    val s="Aman"
    val s1= String(StringBuffer("Aman"))

    println("S string hashcode ${s.hashCode()}")
    println("S1 string hashcode ${s1.hashCode()}")
    println(s==s1) // in kotlin it is true but in java it is false we need to use intern() method to reference the SCP reference.

    // Sub String


    val str="Amandeep Tomar";
    println(str.length)

    val str1=str.substring(1)

    println("str1 $str1") // o/p -> mandeep Tomar

    val str2=str.substring(1,13) // it include last -1 index

    println("str2 $str2") // o/p -> mandeep Toma

    val str3=str.substring(0,14)
    println("str3 $str3") // o/p -> Amandeep Tomar

    println(str==str3) // true

  //  str.substring(-1) // exception
 //   str.subSequence(-1,5) // exceptiomn
   // str.substring(50) // exception
    str.substring(str.length) // empty string

}