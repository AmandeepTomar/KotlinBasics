## Generics

Generics in Kotlin allow you to write reusable code by creating classes, interfaces, and functions
that can work with multiple types. They provide type safety and help in avoiding type-casting errors
at runtime

- Class

```kotlin
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
}

```

- Methods

```kotlin

fun <T> findElements(
    element: T,
    array: Array<T>,
    foundedElement: (index: Int, element: T?) -> Unit
) {
    for (i in array.indices) {
        if (element == array[i]) {
            foundedElement(i, element)
            return
        }
    }
    foundedElement(-1, null)
    return
}

```

#### `in` keyWord Contravariant, In java we achieve with super

- Type parameters can only be `consumed (receive)` values.
- if we have a class with a given super type generic then we can assignees the sub-type generic of
  that super class.
- We can use the super type in place of sub type.
- We can use contravariance, where we want the class or interface use generic type as input. 

```kotlin
interface Persone<in P>{
    
    fun doSomething(p:P) // this will compile.
    
    fun doSomethingAndReturn(p:P) : P // Error , compile time 
    
    val persone:P // compile type error 
}

fun main() {

  // val userComparator:Comparable<User> = adminComparator // it is not compile due to contravariance.

  val adminComparator : Comparable<AdminUser> = userComparator // this will compile
}
val adminComparator :Comparable<AdminUser> = object :Comparable<AdminUser> {
  override fun compareTo(other: AdminUser): Int {
    return 1;
  }
}

public val userComparator :Comparable<User> = object :Comparable<User>{
  override fun compareTo(other: User): Int {
    return 1;
  }
}

```

#### `out` Keyword CoVariance , In java we achieve with extends

- Parameters can only be `produce (return)` values
- Covariance is used to produce the items , that means return the item , if we tried to consume them
  that compiler will show us error at the compiler time itself.
  -`List<out E>`
    - E is the type of elements list contains
    - The list is covariant in its list type , due to this properties we can do in kotlin.
    - ```kotlin
  val intList = listOf(1,2,3)
  val numsList:List<Number> = intList
    ```
    - these line compile in kotlin due to covariance , same will be not compiled in java.
      Examples :

1. here list of user is covariant of its type that is Admin and normal user.
2. This not compile  `val mutableListOfUser:MutableList<User> = mutableListOfAdmin` because //
3. `MutableList<E>`E - the type of elements contained in the List. The **mutable list is invariant
   in its element type**.

```kotlin
fun main() {
    val listOfNormalUser = listOf<NormalUser>()
    val lisOfAdminUser = listOf<AdminUser>()
    val listOfUser: List<User> = listOfNormalUser
    val listOfUser1: List<User> = lisOfAdminUser

}

open class User(name: String)
class AdminUser(name: String) : User(name)
class NormalUser(name: String) : User(name)
// ==========
```

- Here is the Example is use of covariance
- with the help of `@UnsafeVariance` annotation, covariance can consume. but is is not recommended
  until you are sure.

```kotlin
   interface Person<out E> {

    fun getPerson(): E  // compile cos it is producing

    fun setPerson(e: E) // its is not compile due ro covariance property.

    fun setPerson(e: @UnsafeVariance E) // this will compile=. 
}
```

#### `reified` Keyword , for check type at compile time it self.


#### Type Erasure
- Like when we have same-method name and same type of parameters and but with difference type of element. 
```kotlin
//this will not compile, because they have different type of List String , Int with same function name.
fun performList(list:List<String>)
fun performList(list:List<Int>)

// to fix that problem we can use @JvmName(")

@JvmName("performListString")
fun performList(list: List<String>){}

@JvmName("performListInt")
fun performList(list: List<Int>){}


```