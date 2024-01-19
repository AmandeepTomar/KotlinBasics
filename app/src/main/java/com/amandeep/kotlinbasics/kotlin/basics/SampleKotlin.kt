import java.util.concurrent.atomic.AtomicIntegerFieldUpdater

fun main() {

    val list = listOf(1,2,3,4,5,6,7,8,9,0)

    val oddNo = list.partition { it%2==0 }
    println(oddNo)

    val mapResult = list.map { it*2 }
    println(mapResult)

  //  val flatMap = list.flatMap {  }


    // print with index
    for ((index, valie) in list.withIndex()){
        print("$index and $valie ")
    }
        println()
    val flatmapResult = oddNo.toList().flatMap { it }

    println(flatmapResult)

    val lista= listOf(11,12,13,14,15)
    val listb = listOf(1,2,3,4,5)

    val zipResultPair = lista.zip(listb)
    println(zipResultPair)
   val retrnedList =  lista.zip(listb){a,b->a*b}
    println(retrnedList)



    var a = 1
    var b = 1;

//    b = a.also { a=b }
//
//    println("a $a and  $b")



   println("${(a==b)} and ${a===b}")

    val name = "Aama"
    val student1 = Student(name)
    val student2 = Student(name)
    val emp = Employee(name)
    val wmp1 = Employee(name)
   println("${(student1==student2)} and ${student1===student2}")
   println("Employee ${(emp.equals(wmp1))} and ${emp===wmp1}")





}

class Employee(val name:String)

data class Student(val name:String)




