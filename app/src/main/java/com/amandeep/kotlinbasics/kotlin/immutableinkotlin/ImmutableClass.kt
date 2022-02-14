package com.amandeep.kotlinbasics.kotlin

fun main() {
   val data= MyImmutable("Aman",10)

    val data1=MyImmutableWithClass("Test", MyTest("MyTestCass",1, byteArrayOf(1,2,3)))
   //  data1.myTest.count=2 // its breaking properties
    // Now we make all val
    println(data1.myTest.arrayOfBytes.contentToString())
    data1.myTest.arrayOfBytes[2]=10
    println(data1.myTest.arrayOfBytes.contentToString())



}


data class MyImmutable(val name:String,val age:Int)

data class MyImmutableWithClass(val name:String,val myTest: MyTest)


class MyTest(val data: String,val count:Int,byteArray: ByteArray){

    val arrayOfBytes=byteArray
    get() = field.copyOf()

}