package com.amandeep.kotlinbasics.kotlin.collectionsinkotlin


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

val list = listOf(1, 2, 2, 2, 3, 4, 5)
val list2 = listOf("a", "b", "c", "d")
val map = mapOf("a" to 1, "b" to 2, "c" to 3).toMutableMap()

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


    val associatedList:Map<Int,Int> = list.associateBy { it }
    println(associatedList)

    val personAssociatedList:Map<String,Person> = personList.associateBy { it.name }
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

    println("flatten array of arrays")
    arrayOf(arrayOf(1, 2, 3, 4), arrayOf(5, 6, 7, 8), arrayOf(9, 10, 11, 12)).flatten().forEach {
        print(it)
    }

    personList.toSortedSet(compareBy { it.name })

}

fun aggregatorsIsCollections() {
    /**
     * Fold , FoldIndex , FoldRight , FoldRightIndex -> we have support to provide the initial value, like 10 initial and add rest.
     * Reduce , ReduceIndex , ReduceRight , ReduceRightIndex -> it start from beginning and initial value is index 1 value. empty list can not perform reduce
     * ====== Grouping
     * GroupBy , GroupBy(){with new values}  // it can be done by some conditions.
     * GroupByTo  - > Group by first lambda, modify value with second lambda, dump the values to given mutable map
     * GroupingBy -> FoldTo -> , like applyingb eachcount() with  GroupingBy not groupNy.
     * chunked(size)
     * windowed{size , steps , isPartialWindow}
     * Count , Count{}
     * max . maxBy{} , maxByOrNull{},  // this will return person object
     * maxOfOrNull{}
     * maxWith(Comparator<Int>{x,y->
     *     when{
     *     x==1->1
     *     y==1->1
     *     else ->y-x
     *     })
     *   Min , minBy , minWith(), minByOrNull{} // this will return person object
     *   ,mixOfOrNull{} // this will return value as Int not person object
     *   sum , sumBy{} , sumByDouble{}
     * */
    println()

    val list = listOf(1, 2, 3, 4, 5)

    val reduce = list.reduce { acc, value -> acc + value }
    println("Reduce $reduce") // 15
    val reduceIndexed = list.reduceIndexed { index, acc, value ->
        if (index and 1 == 0) acc + value else acc
    }

    println("list.reduceIndexed{index,acc,value} = $reduceIndexed") // 9

    val reduceRight = list.reduceRight { value, acc -> value + acc }
    println("reduceRight $reduceRight") // 15  {5+4+3+2+1}

    val reduceRightIndex =
        list.reduceRightIndexed { index, i, acc -> if (index == 2) acc else i + acc }

    println("reduceRightIndex $reduceRightIndex") //  3 is skipped , and is 12.

    // val reduceWithEmptyList = emptyList<Int>().reduce { acc, i -> acc + i }
    //  println(reduceWithEmptyList) // Empty collection can't be reduced. UnsupportedOperationException

    // fold

    val fold = list.fold(10) { acc, value -> value + acc }
    println("fold with initial value of 10 $fold") //25

    // same all other methods like foldIndexed(10){index , acc , value ->{}}

    val rightFold = list.foldRight(10) { value, acc ->
        print(value)
        value + acc
    }
    println("rightFold $rightFold")
    // same for foldRightIndexed(10){index, value,acc-> }

    // Grouping

    personList.groupBy { it.name }.forEach {
        println("${it.key} -> ${it.value.count()}") // Amandeep -> 3 for all values
    }

    val groupingBy = personList.groupingBy { it.name.first() }.eachCount()
    println("groupingBy $groupingBy") // groupingBy {A=4, K=2, H=1, E=1, G=1}

    personList.groupingBy { it.company }.eachCount()

    val maxAgeInList:Person? = personList.maxByOrNull { it.age } // this will return person object
    val minAgeInList = personList.minByOrNull { it.age }// this will return person object
    val minByAgeInList = personList.minBy { it.age }
    val maxByAgeInList = personList.maxBy { it.age }
    val maxOfAgeInList:Int? =
        personList.maxOfOrNull { it.age } // this will return value as Int not person object
    val minOfAgeInList =
        personList.minOfOrNull { it.age }// this will return value as Int not person object
    println("maxAgeInList in personList $maxAgeInList \n maxAgeInList in personList $minAgeInList ")
    println("maxOfAgeInList in personList $maxOfAgeInList \n minOfAgeInList in personList $minOfAgeInList ")
    println("maxByAgeInList in personList $maxByAgeInList \n minByAgeInList in personList $minByAgeInList ")

    val partionList = personList.partition { it.age <= 24 }
    println(partionList)

    // here we need to find the no if person whoes age is <=24

    val countOfAgeLessThanEqualTo24 = personList.count { it.age <= 24 }
    println("countOfAgeLessThanEqualTo24 $countOfAgeLessThanEqualTo24 ")

    // print the average of the age < = 34


    val personsListOfAgeLessThanEqualOf34 = personList.partition { it.age <= 34 }.first.map {
        it.age
    }
    println(personsListOfAgeLessThanEqualOf34.sorted())
    println("avereage = ${personsListOfAgeLessThanEqualOf34.average()}")
    println("Average Age is ${personsListOfAgeLessThanEqualOf34.sum() / personsListOfAgeLessThanEqualOf34.size}")


    // chunk

    val chuncked = list.chunked(2)
    println(chuncked)

    val windowed = list.windowed(2, 1, false)
    println(windowed)
    val windowed2 = list.windowed(2, 2, false)
    println(windowed2)

}

fun filteringTakeAndDropCollections() {
    /***
     * filter{} , FilterKeys{} , FilterValues{} , FilterIndexed{index,value}
     * FilterIsInstance  -> Type parameter defines the class instance. None returned because in our list all of them are ints
     * Take
     *  take() , takeWhile() , takeLast() , takeLastWhile()
     * Drop
     *  drop() , dropWhile() , dropLast() , dropLastWhile()
     * Element
     *  elementAt() , elementAtOrNull() , elementAtOrElse(){}
     * Get
     *  get() , getOrElse() , getOrNull()[need to check]
     *  Map
     *  get -> [] , getValue() , getOrDefault("1", 10), default value is 10 ,
     *  getOrPut("1"){10}  if we have value at 1 return the value else put 10 on 1.
     * */


    list.filter { it < 4 }.distinct().forEach { print(it) }
    println()
    personList.filter { it.name.startsWith("Am") }.map { it.name + it.age }
        .forEach { print(it.plus(" ")) }
    println()
    map.filterKeys { it != "a" }.values.forEach { print(it) }
    println()
    map.filterValues { it != 2 }.map { it.key + it.value }.forEach { print(it) }
    println()
    list.filterIndexed { index, i -> index == 2 }.forEach { print(it) }

    // take It will take from list
    println()
    list.take(3).forEach { print(it) }
    println()
    list.takeLast(3).forEach { print(it) }
    println()
    list.takeWhile { it < 4 }.forEach { print(it) }
    println()
    list.takeLastWhile { it > 4 }.forEach { print(it) }

    // drop it will drop from the list

    println()
    list.distinct().drop(2).forEach { print(it) } // 345
    println()
    list.distinct().dropWhile { it < 5 }.forEach { print(it) } // 5
    println()
    list.distinct().dropLast(2).forEach { print(it) } // 123

    // Element At

    val elementAt2 = list.distinct().elementAt(2)
    println("elementAt2 = $elementAt2") // 3
    val elementAtOrElse = list.elementAtOrElse(10) { -1 } // -1
    println("elementAtOrElse = $elementAtOrElse")
    val elementOrNull = list.elementAtOrNull(10)
    println("elementOrNull = $elementOrNull") // null

    // map function
    println("map function")
    map.values.forEach { print(it) } // 123
    println()
    val values = map.getOrDefault("d", 1) // 1
    println(values)
    val newMap = map.getOrPut("e") { 5 }
    println(newMap)
    map.values.forEach { print(it) } //123 because it is immutable map

}

fun findInCollections() {
    // finding
    /**
     * find , findLast
     * first first{}, last , last{}, firstOrNull , firstOrNull{}, lastOrNull,lastOrNull{}
     * indexOf , indexOfFirst , indexOfLast
     * */
    // Unions, distinct, intersections
    /**
     * Distinct , DistinctBy{}
     * Intersect
     * Single -> list.single() -> Returns only element or throws.
     * singleOrNull() ->  Throw safe version of single().
     *Union -> list.union(listOf(1,2,3)) ->  [1,2,3,4,5]
     *
     * */
    println()
    val find = list.find { it > 3 }
    println("find $find")
    val findLast = list.distinct().findLast { it > 3 }
    println(findLast)

    val first = list.distinct().first()
    println("first $first") // 1
    val firstPredicate = list.distinct().first { it > 2 }
    println("firstPredicate $firstPredicate") // 3
    val firstOrNull = list.distinct().firstOrNull { it > 10 }
    println("firstOrNull $firstOrNull") // null

    val last = list.distinct().last()
    println("last $last") // 5
   val lastPredicate =  list.distinct().last{it>2}
    println("lastPredicate $lastPredicate") // 5

    val lastOrNull = list.distinct().lastOrNull { it<2}
    println("lastOrNull $lastOrNull") // 1

    // single
    val single = listOf<Int>(10).singleOrNull()
    println("single $single") // 10

    // intercesion

   list.intersect(listOf(1,2,3)).forEach { print(it) } // 123
    println()

    list.union(listOf(6,7,8)).forEach { print(it) } // 12345678

}


fun actionAndCheckOnList(){
    /** forEach , forEachIndexed
     * onEach
     * All , any , none , contains
     * isEmpty , isNotEmpty
     */
    println()
    list.forEachIndexed { index, i -> print(i) }
    println("OnEach")
    list.onEach {
        print(it)
    }
    println()
    val allDigit = list.map { it.toChar() }.all { it.isDigit() } // false // not working with list
    val isAnyDigit = list.none { it.toChar().isLetter() } // true
    val isAnyCharIsDigit = list.any{it.toChar().isDigit()} // false

    println("allDigit $allDigit , isNoneDigitLetter = $isAnyDigit , isAnyCharIsDigit $isAnyCharIsDigit")

    val string = "abc1"
   val isAnyDigitHere  =  string.toCharArray().any { it.isDigit() }
    println(isAnyDigitHere)


}

fun main() {

    transformationsInCollections()
    aggregatorsIsCollections()
    filteringTakeAndDropCollections()
    findInCollections()
    actionAndCheckOnList()
}


data class Person(val name: String, val age: Int, val company: String)