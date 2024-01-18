package com.amandeep.kotlinbasics.kotlin.genericsinkotlin

class FindElement<T>(private val array: Array<T>) {

    fun findElement(element: T, foundedElement: (index: Int, element: T?) -> Unit) {
        for (i in array.indices) {
            if (element == array[i]) {
                foundedElement(i, element)
                return
            }
        }

        foundedElement(-1, null)
        return
    }


    // reified Key works
    inline fun <reified T> checkType(value: Any) {
        if (value is T) {
            println("Value is of type ${T::class.simpleName}")
        } else {
            println("Value is not of type ${T::class.simpleName}")
        }
    }


    // type erasure
    @JvmName("performListString")
    fun performList(list: List<String>){}

    @JvmName("performListInt")
    fun performList(list: List<Int>){}
}
