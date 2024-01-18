package com.amandeep.kotlinbasics.kotlin.genericsinkotlin

class GenericMethodsDemo {

 fun <T> findElements(element:T,array: Array<T>,foundedElement:(index:Int,element:T?)->Unit){
     for (i in array.indices){
         if (element==array[i]){
             foundedElement(i,element)
             return
         }
     }
     foundedElement(-1,null)
     return
 }

}