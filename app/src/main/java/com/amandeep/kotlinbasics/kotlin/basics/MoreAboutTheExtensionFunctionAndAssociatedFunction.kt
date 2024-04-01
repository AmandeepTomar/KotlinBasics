package com.amandeep.kotlinbasics.kotlin.basics


fun main() {

    val data = "Amandeep"
    val isANyUpperCase = data.any { it.isUpperCase() }
    val isAllUpperCase = data.all { it.isUpperCase() }
    println("is Any character is Uppercase in $data  = $isANyUpperCase")
    println("is All character is Uppercase in $data  = $isAllUpperCase")
    val associatedBy = data.associateBy { it }
    println("associatedBy $associatedBy")

    val list = listOf(
        Person("Amandeep Tomar", "Tomar", "Android Developer"),
        Person("Komal Chauhan", "Chauhan", "Product Owner"),
        Person("Ankit Sharma", "Sharma", "Sales Person"),
        Person("Rahul Adata", "Adata", "Developer"),
        Person("Vinod Kumar", "Kumar", "Mobile Shop"),

    )

   val result =  list.sortedBy { it.lastName }.associateBy { it.fullName }
    println(result)


    val listOfNums = listOf(1,2,3,4,5,6,7,8,9,10,11)
   val listOfOffEven  = listOfNums.partition { it%2==0 }
    println(listOfOffEven)
   val flatenList =  listOfOffEven.toList().flatMap { it }
    println(flatenList)

    val (name,lastName) = Pair("Name","LastName") // destructive declaration

    println(name)
    println(lastName)

}

data class Person(val fullName: String, val lastName: String, val title: String)