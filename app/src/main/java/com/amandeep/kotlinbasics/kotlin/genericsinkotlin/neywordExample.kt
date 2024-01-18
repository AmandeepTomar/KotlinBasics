package com.amandeep.kotlinbasics.kotlin.genericsinkotlin

// Contravariant interface using 'in'
interface Feeder<in T> {
    fun feed(animal: T)
}

class AnimalFeeder<in T : Animal> : Feeder<T> {
    override fun feed(animal: T) {
        animal.speak()  // 'T' is assumed to be an Animal, so we can call its methods
    }
}

fun main() {
    val animalFeeder: Feeder<Animal> = AnimalFeeder<Animal>()
    val dogFeeder: Feeder<Dog> = AnimalFeeder<Dog>()
    val catFeeder: Feeder<Cat> = AnimalFeeder<Cat>()

    dogFeeder.feed(Dog())  // Allowed because Dog is a subtype of Animal
    animalFeeder.feed(Dog())  // This is also allowed
}
