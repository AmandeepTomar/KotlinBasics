package com.amandeep.kotlinbasics.kotlin.genericsinkotlin


fun main() {

    // Example of Generic Methods

    val genericMethodsDemo = GenericMethodsDemo()
    genericMethodsDemo.findElements("1",arrayOf("1","2","3","5","6")){ index, element ->
        println("Found Element at Index $index and element $element using Generic methods")
    }


    // Example of Generic classes
    val arrayOfInt = FindElement(arrayOf(1,2,3,4,5))
    arrayOfInt.findElement(3){ index, element ->
        println("Found Element at Index $index and element $element using Generic class")
    }
    // now same class will be call for String


    val arrayOfString = FindElement<String>(arrayOf("1","2","3","5","6"))

    arrayOfString.findElement("4"){index, element ->
        println("Found Element at Index $index and element $element using Generic class")
    }


    checkType<String>("Hello")  // Output: Value is of type String
    checkType<Int>("Hello")

}


// reified Key works
inline fun <reified T> checkType(value: Any) {
    if (value is T) {
        println("Value is of type ${T::class.simpleName}")
    } else {
        println("Value is not of type ${T::class.simpleName}")
    }
}

