package com.amandeep.kotlinbasics.kotlin.basics


/**
 * private
 * public
 * protected
 * internal
 * open
 * */


open class Base{
    private val a=10
    protected open val c=3
    val b=10
    internal val d=1
    protected fun getProtected(){
        print("Protected Function")
    }
}

class Derived :Base(){

    override val c: Int =6

    fun getData(){
        getProtected()
    }

}


fun main() {
    val base = Base()
    println(base.b)
    println(base.d)
   // base.a, base.c , base.getProtected()  // not visible coz a is private , c and getProtected() are protected

    val derived = Derived()
    derived.getData()
   // derived.c  // not visible
}