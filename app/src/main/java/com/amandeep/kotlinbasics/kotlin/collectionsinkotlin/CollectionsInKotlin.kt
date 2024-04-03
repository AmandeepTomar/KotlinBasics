package com.amandeep.kotlinbasics.kotlin.collectionsinkotlin


fun transformationsInCollections() {
    /*
    * associated()
    * associatedBy{}
    * map,mapNotNull , mapIndex
    * flatten()
    * flattenMap{}
    * reversed()
    * sortedBy{}
    * sortedByDescending{}
    * sortedWith(Comparator<Int>{x,y->})
    * slice
    * partition
    * mapValues
    * mapKeys
    * zip
    * unzip
    * */


    val list = listOf(1, 2, 2, 2, 3, 4, 5)
    val list2 = listOf("a", "b", "c", "d")
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    val associatedList = list.associateBy { it }
    println(associatedList)

    val personList = listOf(
        Person("Amandeep", company = "Amandeep Tomaer", age = 31),
        Person("Amandeep", company = "Amandeep Comany", age = 32),
        Person("Amandeep", company = "Amandeep data ", age = 24),
        Person("Komal", company = "Amandeep Chauhan", age = 27),
        Person("Himanshu", company = "Amandeep Chauhan", age = 25),
        Person("Kajol", company = "Amandeep Chauhan", age = 24),
        Person("Ekansh", company = "Amandeep Shrivastav", age = 36),
        Person("Annu", company = "Amandeep Shrivastav", age = 32),
        Person("Gaurav", company = "Gaurav Chauhan", age = 33),
    )

    val personAssociatedList = personList.associateBy { it.name }
    println(personAssociatedList)

    val associate = list.associateBy { it }
    println("associateBy $associate")

    val updatedList = list.map { it + 1 }
    println("Use of Map $updatedList")

    val mapIndex = list.mapIndexed { index, it ->
        if (index == 2) it * 2 else null
    }
    println("mapIndex $mapIndex")

    val listOfPartitions = list.partition { it and 1 == 0 }
    println("listOfPartitions $listOfPartitions")
    val sliceList = list.slice(1..5)
    println("sliceList $sliceList")

    val flattenList = listOf(list, list2).flatten()
    println("flattenList $flattenList")

    val flattenListWithMap = listOf(list2, list).flatMap {
        it + list2
    }
    println("flattenListWithMap $flattenListWithMap")

    val mapOfkeys = map.mapKeys { (key, values) -> key + values } // {a1=1, b2=2, c3=3}
    println(mapOfkeys)
    val mapOfValues =
        map.mapValues { (key, Values) -> Values * 2 } // {a=2, b=4, c=6} // coz multiply by 2.
    println(mapOfValues)

    val zippedList = list.zip(list2)
    println("ZippedList $zippedList")  //  [(1, a), (2, b), (2, c), (2, d)]

    val unZipList = zippedList.unzip()
    println("unZipList $unZipList") // ([1, 2, 2, 2], [a, b, c, d])

}

fun aggregatorsIsCollections() {
    /**
     * Fold , FoldIndex , FoldRight , FoldRightIndex
     * Reduce , ReduceIndex , ReduceRight , ReduceRightIndex
     * ====== Grouping
     * GroupBy , GroupBy(){with new values}
     * GroupByTo  - > Group by first lambda, modify value with second lambda, dump the values to given mutable map
     * GroupingBy -> FoldTo ->
     * Count , Count{}
     * max . maxBy{}
     * maxWith(Comparator<Int>{x,y->
     *     when{
     *     x==1->1
     *     y==1->1
     *     else ->y-x
     *     })
     *   Min , minBy , minWith()
     *   sum , sumBy{} , sumByDouble{}
     * */
}

fun main() {

    transformationsInCollections()
    aggregatorsIsCollections()
}


data class Person(val name: String, val age: Int, val company: String)