package com.amandeep.kotlinbasics.kotlin.genericsinkotlin

// Animal class and its subtypes
open class Animal {
    open fun speak() {
        println("Animal speaks")
    }
}

class Dog : Animal() {
    override fun speak() {
        println("Woof!")
    }
}

class Cat : Animal() {
    override fun speak() {
        println("Meow!")
    }
}

// Covariant interface using 'out'
interface Container<out T> {
    fun get(): T
}

class AnimalContainer<out T : Animal>(private val animal: T) : Container<T> {
    override fun get(): T = animal
}

fun main() {
    val dogContainer: Container<Dog> = AnimalContainer(Dog())
    val catContainer: Container<Cat> = AnimalContainer(Cat())

    val animalContainer: Container<Animal> = dogContainer // Allowed due to covariance

    animalContainer.get().speak()  // Output will be Woof! or Meow! depending on the assignment above

    // Now List<User> = listOfNormalUser

    val listOfNormalUser = listOf<NormalUser>()
    val lisOfAdminUser = listOf<AdminUser>()
    val listOfUser:List<User> = listOfNormalUser
    val listOfUser1:List<User> = lisOfAdminUser
    // here list of user is covariant of its type that is Admin and normal user.

    val mutableListOfAdmin = mutableListOf<AdminUser>()
    val mutableListOfNormal = mutableListOf<NormalUser>()

    val listOfUser2 :List<User>  = mutableListOfAdmin // this of works

   // val mutableListOfUser:MutableList<User> = mutableListOfAdmin // it is not works

   // E - the type of elements contained in the list. The mutable list is invariant in its element type.


}



open class User(name:String)
class AdminUser(name: String):User(name)
class NormalUser(name: String):User(name)

interface Person<out E>{

    fun getPerson() : E  // compile cos it is producing

    fun setPerson(e :@UnsafeVariance E) // its is not compile due ro covariance property.
}
