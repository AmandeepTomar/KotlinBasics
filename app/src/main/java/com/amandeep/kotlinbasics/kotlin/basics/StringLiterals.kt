package com.amandeep.kotlinbasics.kotlin.basics

fun main() {
  /*
    String literal
    Kotlin has two types of string literals:

    - escaped strings that may contain escaped characters
            - Escaping is done in the conventional way, with a backslash (\).
             See Characters above for the list of supported escape sequences.

    - raw strings that can contain newlines and arbitrary text
            -A raw string is delimited by a triple quote ("""),
            contains no escaping and can contain newlines and any other characters

            - To remove leading whitespace from raw strings, use the trimMargin() function:
            - By default, | is used as margin prefix, but you can choose another character and pass it as a parameter,
                like trimMargin(">").
    */


    println("escapedString = ${escapedString()}")
    println("rawString = ${rawString()}")
    println("rawString_remove_leading_whitespace = ${rawStringremove_leading_whitespace()}")

}

fun escapedString():String="Hello, world!\n"

fun rawString():String="""
    for (c in "foo")
        print(c)
"""

fun rawStringremove_leading_whitespace():String="""
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()

//o/p ->

/**
 * escapedString = Hello, world!

rawString =
for (c in "foo")
print(c)

rawString_remove_leading_whitespace = Tell me and I forget.
Teach me and I remember.
Involve me and I learn.
(Benjamin Franklin)
 * */