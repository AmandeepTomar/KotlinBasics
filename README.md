# Kotlin Basics

- Arrays
- Sealed class and interfaces
- Mutable and immutable classes
- Strings
- Val , var , const , lambda , high order function.
- Coroutine and their working
- flow and operators

## Lambda and Higher order function in Kotlin

- In kotlin we call kotlin functions as first class citizen , like we can store function in
  variable , we cam pass
  lambda to function , pass function as parameter.
- take function as input or return function as output
- A function that does not have a name called lambda `val sum = {x:Int,y:Int -> x+y}`

```kotlin
fun main() {
    calculate(2, 3, ::sum)
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun calculate(a: Int, b: Int, add: (Int, Int) -> Int) {
    val sum = add(a, b)
    println(sum)
}
```

## Extension function with

- We do not need to extends the object to extends is current functionality in kotlin.

```kotlin

val person = Person().myFunction {
    name = "My Name"
    sirName = "My Sir Name"
}

class Person {
    var name: String = ""
    var sirName: String = ""
}

//extension function 

fun <T> T.myFunction(block: T.() -> Unit): T {
    block()
    return this
}

```

## Scope functions

- Scope functions are high order function in kotlin.
- Scope function that return the lambda
    - let , run , with
- Scope function that return the object it self
    - also , apply
- Scope function that object reference to `this`
    - with , run and apply.
- Scope function that object reference to `it`
    - let , also.
    - **let**
        - it will use to check the nullability of calling object.
        - context of object refer by it.
        - return the lamda, last like of block.
    - **also**
        - Similar to let , context object refer by it.
        - return the same instance of object.
        - its like we do and also do it.
    - **run**
        - Similar to apply,
        - contest object refer to this.
        - return the lambda , last line of block.
    - **apply**
        - its like apply the modification on object, it is used to modify the property of object.
        - object context refer by this
        - return the object itself
    - **with**
        - similar to run,
        - context object refer with this.
        - return lambda
- Example

```kotlin

var name: String? = "Initial"

val student = Student()

val length: Int = name?.let {
    // return lambda
    val newValue = it.plus("Name")
    newValue.length
} ?: 0

println("length with null safety and with let $length")

val studentRun: String = Student().run {
    // return lambda
    name = "Name Run"
    age = 151
    "$name and $age WithRun"
}

println("studentRun $studentRun")

val inWords: String = with(Student()) {
    name = "With Name"
    age = 10
    // return lambda
    "name $name and Age $age"
}


val studentAfterAlso: Student = student.also {
    // return same object
    "Name of $it" // no use
    it.age = 10
}

println("StudentAfterAlso $studentAfterAlso")

val studentApply: Student = Student().apply {
    // return Same object
    name = "Apply Name"
    age = 15
}

```

## Backing Properties and Fields

- Backing Field
    - Backing field in kotlin represent by `field` keywords, it will use to store the value of its
      own property.
    - We can use `field` backing field only with getter and setter in kotlin.
    - if we use the property name within the property getter and setter it will
      throw `StackOverFlowException` because it will call itself in recursion.
    - Backing field is generated when the are call with in the getter and setter or that its own
      property.
    - Example: this will not create the backing field because it is using someother property not
      included field in this getter.
    ```kotlin
        val isPass: Boolean
      get() = marks > 30
    var name:String = "" // No backing field generated.
    ```
    - Example:- in this example we are using `field` keywords so the backing field is generated.
  ```kotlin
        var name: String = "NO Name"
        get() {
            println("Name Student => $field")
            return "Complete Name  => $field"
        }
        set(value) {
            field = if (marks > 80) "Good Student $value"
            else value
        }
  ```
- **Backing Properties**
    - This one is like we will provide the immutable property to the outside so the dataset change
      will be done by its own class only.
    - Example -> do the things in viewModel when we expose the data by flow or live data.

```kotlin

class Student {

    private var _name: String = ""
    val name: String get() = _name

    // We can do the changes if we do this. 
    var courseName: String = ""
        get() = _courseName // backing property 
    //after this we will get the same value not updated because
// in get() we are returning the _courseName. 
}

fun main() {
    val student = Student()
    student.name
    student.name = "hello" // its can not be compile, as it is val.
}


```

## Sealed class and interface

- `Sealed classes and interfaces` represent restricted class hierarchies that provide more control
  over inheritance. All direct subclasses of a sealed class are known at compile time.
- For example, third-party clients can't extend your sealed class in their code.
- The same works for `sealed interfaces` and their implementations: once a module with a sealed
  interface is compiled, no new implementations can appear.
- sealed classes are similar to enum classes: the set of values for an enum type is also restricted,
  but each enum constant exists only as a single instance, whereas a subclass of a sealed class can
  have multiple instances, each with its own state
- Direct subclasses of sealed classes and interfaces must be declared in the same package.
- Example:-

```kotlin
    sealed interface Error

sealed class IOError() : Error

class FileReadError(val f: File) : IOError()
class DatabaseError(val source: DataSource) : IOError()

object RuntimeError : Error
```  

- Sealed classes and when expression

```kotlin
    fun log(e: Error) = when (e) {
    is FileReadError -> {
        println("Error while reading file ${e.file}")
    }
    is DatabaseError -> {
        println("Error while reading from database ${e.source}")
    }
    RuntimeError -> {
        println("Runtime error")
    }
    // the `else` clause is not required because all the cases are covered
}
```

#### Why Sealed Class over Abstract Class?

- Sealed classes are abstract by itself, and cannot be instantiated directly. If Sealed classes are
  abstract by default, why can’t we use an abstract class instead of a
  sealed class in the first place? Well, the catch here is an abstract class can have their
  hierarchies anywhere in the project, whereas a sealed class should contain all the hierarchies in
  the same file.
- Another important advantage of using a sealed class over abstract class is that it helps the IDE
  to understand the different types involved and thereby helps the user in auto-filling and avoiding
  spell mistakes.

==================================================================

# Coroutine

A coroutine is an instance of a suspendable computation. It is conceptually similar to a thread, in
the sense that it takes a block of code to run that works concurrently with the rest of the code.
However, a coroutine is not bound to any particular thread. It may suspend its execution in one
thread and resume in another one.

- A coroutine is light-weighted thread.
- Coroutine can be suspend and resumed , while suspending they do not block any threads.

#### Structured Concurrency

- Every coroutine need to be started in a logical scope with a limited life time. If the life time
  of scope end/cancel all the coroutine launch with in the scope are not completed yet will be
  cancelled.
- Coroutine started in the same scope form a hierarchy.
- A parent Job won't complete until all the child job get completed.
- Cancelling a parent job will cancel all the child.
- Cancelling a child will not cancel the parent.
- **Ordinary JOB** if a child coroutine get failed , exception is propagated upwards and cancel all
  its jobs.
- **Supervisor JOB** if a child coroutine get failed , exception is not propagated upwards and do
  not
  cancel all its jobs.
    - The `SupervisorJob` allows you to define a hierarchy of coroutines where the failure or
      cancellation of one coroutine does not affect the others.

#### Suspending function

- Suspending function , A function with a `suspend` modifier called suspended function.
    - It is call from another suspended function or coroutine scope.
    - It can not be call from normal function.
    - We can call the normal function from inside a coroutine.

```kotlin

val scope = CoroutineScope(Job())
fun main() {

    runBlocking { // this on execute on Main thread
        scope.launch {// create new thread T1
            executeTask()
        }
        delay(200) // on main thread.
    }

}

suspend fun executeTask() {
    doSomeLongRunningTask()
}

fun doSomeLongRunningTask() {
    print("Hello ${Thread.currentThread().name}")
}
```

### Coroutine Builders

- **launch**
- Launches a new coroutine without blocking the main thread.
- it is return the Job object, with this we can control the coroutine by
  using `job.join() , job.cancel()` and so more.
- launch builder by default launch on the thread on which it get called. if it get called from main
  thread then inside launch the thread will be main.
- Used to launch the coroutine with scope or local scope. like `viewModelScope` , fragmentScope or
  activityScope.
    - `GlobalScope.launch()`
    - Global scope means that this scope will live as the life of application. Global coroutine this
      one is app level.
        - download file
        - play music there are some use cases for this.
        - This one is not recommended , coz it will always run.
- **Async**
    - Active concurrent Execution
    - launch a new coroutine without blocking the main thread. coroutine scope is the scope from
      where it launched.
    - `GlobalScope.async()` -> globally.
    - `async` -> local
    - it is return `Deferred<T>` object. when we call the `async{}` use `await()` to get the result.
      if you do not want the result or return from the method just
      call `deferredObj.join() and deferredObj.cancel() `.
    - `Deferred` it is an `interface` and subclass of `Job interface`
- **runBlocking**
    - it is blocking the thread, so if we doing something heavy on runBlocking scope on main thread
      will be blocked.
    - used to write the test cases.

### Coroutine Cancellation

**To cancel a coroutine , It should be cooperative**

- `delay(),yield(),withContext(),withTimeout(), CoroutineScope.isActive ` these are the function
  that makes a coroutine cooperative.
- `job.cancelAndJoin()` , it will cancel the coroutine if coroutine is cooperative , else wait to
  finish the coroutine.
- `CoroutineScope.isActive()` to check the coroutine is active or not.

### Coroutine Exception Handling

- We can handle by `try{ } catch{} throw CancellationException`. Any function that is cancelable
  from coroutine package will throw the `CancellationException` , when it got cancelled.
- We can not run a `suspended` function from the `finally{}` block.
- If we want to execute some `suspend` function from `finally{}` block than warn the code
  with `wthContext(NonCancelable)`
    - ```kotlin
        try{
  }catch(e : CancellationException){
  }finally{
  withContext(NonCancelable){
  suspendfun()
  }
  }
    ```

### Timeouts

- **WithTimeOut**
    - this one is also a `coroutine builder` just like a launch and async.
    - ```kotlin
        
  withTimeOut(200){
  for(i in 1..1000){
  print(i)
  delay(400)
  }
  }
    ```
- if the code is not executed with in `200ms` then `CancellationException`will be thrown.
- **withTimeoutOrNull**
    - if the time is not executed in the given time then it returned the value null and  "This Value
      is returned".
- ```kotlin
        
   val returnedValue =  withTimeOutOrNull(200){
            for(i in 1..1000){
            print(i)
    delay(400)
    }
  "This Value is returned"
        }
    ```

### Sequential Coroutine Execution

- by default coroutine launched in sequential manner.

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main Start ${Thread.currentThread().name}")
    val time = measureTimeMillis {
        val m1 = returnMessageOne()
        val m2 = returnMessageTwo()
        print("Result $m1 and $m2")
    }
    println("measureTime in $time ms")
    print("End Start ${Thread.currentThread().name}")
}

suspend fun returnMessageOne(): String {
    delay(1000)
    println("returnMessageOne")
    return "MessageOne"
}

suspend fun returnMessageTwo(): String {
    delay(1000)
    println("returnMessageTwo")
    return "MessageTwo"
}
```

### Concurrent Coroutine Execution

- with the help of async we can launch coroutine in concurrent way.
- using launch builder we can also achieve the concurrency.

```kotlin
fun main() = runBlocking {
    println("Main Start ${Thread.currentThread().name}")
    val time = measureTimeMillis {
        val m1 = async { returnMessageOne() }
        val m2 = async { returnMessageTwo() }
        print("Result ${m1.await()} and ${m2.await()}")
        // we can do the same with launch 
    }
    println("measureTime in $time ms")
    print("End Start ${Thread.currentThread().name}")
}

```

### Lazy Coroutine Execution

- When we call the coroutine using `async{}` and not use their result like `result.get()` still the
  suspended method call inside async{} get called , so it is waste of resources so at thi time the
  lazy initialization of coroutine scope comes in picture.

```kotlin
fun main() = runBlocking {

    println("Main Start ${Thread.currentThread().name}")
    val m1 = async(start = CoroutineStart.LAZY) { returnMessageOne() }
    val m2 = async(start = CoroutineStart.LAZY) { returnMessageTwo() }
    println("Result ${m1.await()} and ${m2.await()}")
    print("End Start ${Thread.currentThread().name}")

}

```

### Coroutine Scope

- Coroutine Scope means when you launch a coroutine a coroutine scope created that tell us which
  kind of coroutine is launched.
- `launch{this:coroutineScope}` same with async and any builder.
- Each coroutine has its own coroutine scope.

### Coroutine Context

- Every coroutine has a coroutine context. an interface in Kotlin that defines the behavior of a
  coroutine using a collection of elements
- The important elements of CoroutineContext are:
    - Job , CoroutineDispatcher, CoroutineName, CoroutineExceptionHandler

### Coroutine Dispatchers

- Dispatchers.MAIN
- Dispatchers.IO
- Dispatchers.DEFAULT
- Dispatchers.UNCONFINED

### Coroutine Exceptions

- When the exception occur it cancel itself and propagated the exception to its parent launch.

```kotlin
// In this example we will get the exception that will caught by `handler`.
// Before caught the exception it will complete the Success, because it will delay 400 in first launch.
// if that delay is less 10 then both launch will be cancelled. 

val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.e("TAG", "handleException : ${throwable.cause}")
}
lifecycleScope.launch(handler) {
    launch {
        launch {
            delay(400)
            throw Exception("Error")
        }
    }

    launch {
        delay(100)
        println("Success ")
    }
}
```

In this Example launch is wrapped by `supervisorScope` so only that job get exception will be
cancelled and others will be called successfully and print values.

```kotlin

val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.e("TAG", "handleException : ${throwable.cause}")
}
lifecycleScope.launch(Dispatchers.Main + handler) {
    supervisorScope {
        launch {
            launch {
                delay(400)
                throw Exception("Coroutine 1 Error")
            }
        }

        launch {
            delay(100)
            println("Coroutine 2 sccess")
        }

        launch {
            delay(400)
            println("Coroutine 3 sccess")
        }
    }

}
```

#### Problem

Suppose we have a coroutine job that is cancel after some time and the network call is in the way
than What happen to the coroutine?

- NOTE: - When we hit the `job.cancel()`, cancellation required cooperation, when we are doing
  something that is taking time and like reading list of files , We have to make our coroutine
  cooperative to make sure the cancelable will works. So we have to check at time of felting file
  that coroutine is active or not. by using `ensureActive()` or `yield()`.
- As we launched the coroutine and its doing some long running task job and we cancel the job. so it
  will throw the `CancelationException` so we need catch that exception specifically. or we need to
  catch the specif exceptions.
- If we catch with `Exception` only then it will catch the `CancellationException` Also and job is
  running and doing the jobs but that should not the behaviour.
- Check the code in below. Solution.
- Correct way to doing cancel.

```kotlin
    lifecycleScope.launch {
    val job = launch {
        try {
            delay(1000)
        } catch (e: HttpRetryException) {
            e.printStackTrace()
        }
        // this code will be called because we catch the CancellationException so that will not perform its cancellation.
        println("Nope cant print")
    }

    delay(100)
    job.cancel()
}
```

### Q. How `suspend` function works internally.

- it is a special function that can be suspended without blocking the main thread.
- Under the Hood, When we call the suspend function then a parameter added in function that
  is `Continuation`.

```kotlin
suspend fun callHere() {
    delay(100)
}
// After Compilation 

public static final Object callHere(@NotNull Continuation var1) {
    Object $continuation;
    // do stuff here 
    $continuation = new ContinuationImpl(var1)
}
```

- `Continuation ` is taking care of context of suspension and resume. this have the information when
  this one is need to resume. just like a callback for you provided by coroutine.
- Its called `Continuation-Passing-Style` its like a Continuation State Machine
- call load -> suspend , state = 1
- call load and when task is done -> resume state = 0
- exit.

- When suspend function return something that means `Work is Completed`
- When Scope is cancelled that means all children in that scope also cancelled.

### Example How Cancel works in coroutine

```kotlin
suspend fun work() {
    val startTime = System.currentTimeMillis()
    var nextPrintTime = startTime
    var i = 0
    while (i < 5) {
        yield() // due to this it will not printr 4, 5 
        // print a message twice a second
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("Hello ${i++}")
            nextPrintTime += 500L
        }
    }
}
fun main(args: Array<String>) = runBlocking<Unit> {
    val job = launch(Dispatchers.Default) {
        try {
            work()
        } catch (e: CancellationException) {
            println("Work cancelled!")
        } finally {
            println("Clean up!")
        }
    }
    delay(1000L)
    println("Cancel!")
    job.cancel()
    println("Done!")
}

o / p
Hello 0
Hello 1
Hello 2
Cancel!
Done!
Work cancelled !
Clean up !

import kotlinx . coroutines . *

fun main(args: Array<String>) = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5 && isActive) {
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Hello ${i++}")
                nextPrintTime += 500L
            }
        }
        // the coroutine work is completed so we can cleanup
        println("Clean up!")

    }
    delay(1000L)
    println("Cancel!")
    job.cancel()
    println("Done!")
}

// o/p 
Hello 0
Hello 1
Hello 2
Cancel!
Done!
Clean up !

```

### suspendCoroutine, suspendCancellableCoroutine

- suspendCoroutine and suspendCancellableCoroutine are functions used to create suspending functions
  from callback-based or future-based asynchronous operations.
```kotlin
suspend fun downloadFile(url: String): ByteArray {
    return suspendCoroutine { continuation ->
        val request = URL(url).openConnection() as HttpURLConnection
        request.setRequestProperty("Accept", "application/octet-stream")
        request.connectAsync { connection ->
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bytes = inputStream.readBytes()
                continuation.resume(bytes) // Resume with downloaded bytes
            } else {
                continuation.resumeWithException(IOException("Download failed"))
            }
        }
    }
}
```
-  Similar to suspendCoroutine, but provides built-in cancellation support.

```kotlin
suspend fun downloadFileWithCancellation(url: String): ByteArray {
    return suspendCancellableCoroutine { continuation ->
        val request = URL(url).openConnection() as HttpURLConnection
        request.setRequestProperty("Accept", "application/octet-stream")

        continuation.invokeOnCancellation {
            request.disconnect() // Cleanup on cancellation
        }

        request.connectAsync { connection ->
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bytes = inputStream.readBytes()
                continuation.resume(bytes) // Resume with downloaded bytes
            } else {
                continuation.resumeWithException(IOException("Download failed"))
            }
        }
    }
}
```

Must Read.
https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
https://www.youtube.com/watch?v=w0kfnydnFWI

====================================================================

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
- It allows you to use a more generic subtype where a less generic supertype is expected

```kotlin
interface Persone<in P> {

    fun doSomething(p: P) // this will compile.

    fun doSomethingAndReturn(p: P): P // Error , compile time 

    val persone: P // compile type error 
}

fun main() {

    // val userComparator:Comparable<User> = adminComparator // it is not compile due to contravariance.

    val adminComparator: Comparable<AdminUser> = userComparator // this will compile
}
val adminComparator: Comparable<AdminUser> = object : Comparable<AdminUser> {
    override fun compareTo(other: AdminUser): Int {
        return 1;
    }
}

public val userComparator: Comparable<User> = object : Comparable<User> {
    override fun compareTo(other: User): Int {
        return 1;
    }
}

```

#### `out` Keyword CoVariance , In java we achieve with extends

- Parameters can only be `produce (return)` values
- Covariance is used to produce the items , that means return the item , if we tried to consume them
  that compiler will show us error at the compiler time itself.
- where you want to return more specific subtypes based on the implementation.
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

```kotlin


inline fun <reified T> checkType(value: Any) {
    if (value is T) {
        println("Value is of type ${T::class.simpleName}")
    } else {
        println("Value is not of type ${T::class.simpleName}")
    }
}
```

#### Type Erasure

- Like when we have same-method name and same type of parameters and but with difference type of
  element.

```kotlin
//this will not compile, because they have different type of List String , Int with same function name.
fun performList(list: List<String>)
fun performList(list: List<Int>)

// to fix that problem we can use @JvmName(")

@JvmName("performListString")
fun performList(list: List<String>) {
}

@JvmName("performListInt")
fun performList(list: List<Int>) {
}


```

## Inline , crossinline , noinline , where

- `Inline`
- We used inline keyword in function that take parameter as lambda. if we will not mark these
  function as inline then it will take memory and just call like function.
- it cut the body , and paste in function.
- inline function copy past complete code in function body it has issue,
- like we write two function , in below example the second method is nol call. because first one
  return.as it copy and paste in function so it will act as local return for main().
- to prevent this `crossinline` , To prevent to non-local return.
- `noinline` it will treat the lambda as function. also return is non-local.

```kotlin
fun main() {

    printMessage {
        println("Messagge")
        return
    }

    printMessage { print("SecondMessage") }

}


inline fun printMessage(a: () -> Unit) {
    a.invoke()
}

inline fun applyTransformation(
    videos: List<Video>,
    noinline transformation: (Video) -> Video, // do not want this fun as inline
    crossinline onComplete: (List<Video>) -> Unit // prevent non local return
) {
    onComplete(videos.map(transformation))
}

```

## Reified

- When we required type in side function in generics than we need to use that.
- it is always used with inline.

```kotlin

inline fun <reified T : video> filterList(list: List<Video>): List<T> {
    return list.filterIsInstance<T>()
}
```

## Where

- We want to restrict the type of genetic types

```kotlin
class PlayVideo<T> where T : Video {

}
```

## Flow

- FLow is a coroutine that can emit multiple values over a period of times.
- it is cold flow, that means if any one is not collection that flow than it is not emitting.

### FLOW Operator

- transform the flow
- `flow.filter{ time%2==0}`
- `map{time*time}`
- `onEach` its not transform
- `reduce{accumulator,value->accumulator+value}` adding all elements of list.{1,2,3,4,5} = 15
- `fold(100){accumulator,value->accumulator+value}` => 115.
- `flatmapCombine{}`
- `flatmapMerge{}`
- `flatmaplatest{}`
- `buffer()`Like we have a flow that is ordering the food and another on is consuming the food. so
  both produces and consumer run on different coroutines.
- `conflate()` it will skip.
- `collectLatst{}` collecting the latest.

### Shared flow and State flow

## State Flow

- it is used to keep the State. its not lifecycle aware , it is similar like live data.
- it is hot flow. it will also do something if there is no collector.
- it has default value
-

## Shared Flow

- IF we want to share the flow between multiple collectors than we should use this.
- it is also hot flow.
- we should use collect in place of collectLatest because we do not want to drop any events.
- if we emit shared flow first than collect than it will lost events.
- So first collect the events than emit. means collector should be registered.
- all the one time events just like you do not want to show the error snack-bar again and again,
  navigation events again and again.

## Combine

- it is used to combine two flow together.
- Like we have a user , that has some post and need to check isAuthenticated or not.
- combine is called when either of isAuthenticated or user get changed.

```kotlin
isAuthenticated.combine(user) { isAuthenticated, user ->
    if (isAuthenticated) user else null
}.combine(post) { user, post ->
    createNewObject(user, post)
}.launchIn(viewModelScope)

```

## Zip

it will wit for the emission of both flow when both flow will emit then the zip{}. is called.

```kotlin

flow1.zip(flow2) { flow1, floe2 ->
    print(flow1, flow2)
}
```

## Merge

- it will merge all flow into one flow.
- All the should be of same toe else it rerun any type.

```kotlin
merge(flow1, flow2).onEach {

}
```

Channel

- if we have just one collector than we should use the channel.

### Collections in Kotlin

- in kotlin we have `kotlin.collection` package
- Lis
    - mutableListOf()
- Set
  - 
- Map

## Shallow Copy Vs Deep Copy

#### Shallow Copy

it copy the original object and not create the new object for the nested one, copy as reference, if
original nested object got changed than copied object value get changed

#### Deep Copy

It copy the original object and also create the new copy or nested objects so when we change in the
original copy no changes reflect on copied copy.

```kotlin
fun main() {
    // it copy the original object and not create the new object for the nested one, copy as reference, if original nested object got changed than copied object value get changed
    // shallow copy
    val address = AddressTest("Noida")
    val original = PersonTest("Amandeep", address)
    val copied = original.copy(name = "Tomar")
    println(original)
    println(copied)
    address.city = "Banglore"
    println(original)
    println(copied) // here copied values of address get changed

    println("deep copy")
    // deep copy
    val address1 = AddressTest("Banglore")
    val original1 = PersonTest("Amandeep", address)
    val copied1 = original.clone()
    println(original1)
    println(copied1)
    original1.address.city = "New York"
    println(original1)
    println(copied1)

}

// shallow Copy
data class PersonTest(val name: String, var address: AddressTest) : Cloneable {

    public override fun clone(): PersonTest {
        return PersonTest(name, address.clone())
    }

}
data class AddressTest(var city: String) : Cloneable {
    public override fun clone(): AddressTest {
        return super.clone() as AddressTest
    }
}

```

## Kotlin collections functions

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
      *   ,minOfOrNull{} // this will return value as Int not person object
      *   sum , sumBy{} , sumByDouble{}
    
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
    
        * find , findLast
     * first first{}, last , last{}, firstOrNull , firstOrNull{}, lastOrNull,lastOrNull{}
     * indexOf , indexOfFirst , indexOfLast
     *
    // Unions, distinct, intersections

     * Distinct , DistinctBy{}
     * Intersect
     * Single -> list.single() -> Returns only element or throws.
     * singleOrNull() ->  Throw safe version of single().
     *Union -> list.union(listOf(1,2,3)) ->  [1,2,3,4,5]

     * onEach
     * All , any , none , contains
     * isEmpty , isNotEmpty

```kotlin
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


    val associatedList = list.associateBy { it }
    println(associatedList)

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

    val maxAgeInList = personList.maxByOrNull { it.age } // this will return person object
    val minAgeInList = personList.minByOrNull { it.age }// this will return person object
    val minByAgeInList = personList.minBy { it.age }
    val maxByAgeInList = personList.maxBy { it.age }
    val maxOfAgeInList =
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
    val lastPredicate = list.distinct().last { it > 2 }
    println("lastPredicate $lastPredicate") // 5

    val lastOrNull = list.distinct().lastOrNull { it < 2 }
    println("lastOrNull $lastOrNull") // 1

    // single
    val single = listOf<Int>(10).singleOrNull()
    println("single $single") // 10

    // intercesion

    list.intersect(listOf(1, 2, 3)).forEach { print(it) } // 123
    println()

    list.union(listOf(6, 7, 8)).forEach { print(it) } // 12345678

}


fun actionAndCheckOnList() {
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
    val isAnyCharIsDigit = list.any { it.toChar().isDigit() } // false

    println("allDigit $allDigit , isNoneDigitLetter = $isAnyDigit , isAnyCharIsDigit $isAnyCharIsDigit")

    val string = "abc1"
    val isAnyDigitHere = string.toCharArray().any { it.isDigit() }
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
```





  
