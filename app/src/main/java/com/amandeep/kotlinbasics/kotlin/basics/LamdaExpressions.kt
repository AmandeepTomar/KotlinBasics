package com.amandeep.kotlinbasics.kotlin.basics

fun main() {
    addTwoNo(5, 10)
    // lamda function
    println(sum(5,10))
    add(10,5)

    val lmbdaEmp:(Int,Int)->Int = {x:Int,y:Int->x+y}

   val lambdaSum =  lambdaAdParams(5,50, lmbdaEmp)
    println(lambdaSum)


}

fun lambdaAdParams(a:Int,b:Int,myLambda:(Int,Int)->Int):Int{
    return  myLambda(a,b)
}

// Normal Function
fun addTwoNo(a: Int,b: Int){
    val add= a+b;
    println(add)
}

// lamda function
val sum :(Int,Int)->Int={a:Int,b:Int->a+b}
//lamda function
val add = {a:Int,b:Int-> print(a+b)}