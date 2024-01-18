# Kotlin Basics 
- Arrays
- Sealed class and interfaces
- Mutable and immutable casses
- Strings
- Val , var , const , lamda , highorder function.
- Coroutine and their working


## Sealed class and interface
- `Sealed classes and interfaces` represent restricted class hierarchies that provide more control over inheritance. All direct subclasses of a sealed class are known at compile time.
- For example, third-party clients can't extend your sealed class in their code.
- The same works for `sealed interfaces` and their implementations: once a module with a sealed interface is compiled, no new implementations can appear.
- sealed classes are similar to enum classes: the set of values for an enum type is also restricted, but each enum constant exists only as a single instance, whereas a subclass of a sealed class can have multiple instances, each with its own state
- Direct subclasses of sealed classes and interfaces must be declared in the same package. 
- Example:-
```kotlin
    sealed interface Error
    
    sealed class IOError(): Error
    
    class FileReadError(val f: File): IOError()
    class DatabaseError(val source: DataSource): IOError()
    
    object RuntimeError : Error
```  

- Sealed classes and when expression 
```kotlin
    fun log(e: Error) = when(e) {
        is FileReadError -> { println("Error while reading file ${e.file}") }
        is DatabaseError -> { println("Error while reading from database ${e.source}") }
        RuntimeError ->  { println("Runtime error") }
        // the `else` clause is not required because all the cases are covered
    }
```


#### Why Sealed Class over Abstract Class?
- Sealed classes are abstract by itself, and cannot be instantiated directly. So let’s take a pause here. If Sealed classes are abstract by default, why can’t we use an abstract class instead of a sealed class in the first place? Well, the catch here is an abstract class can have their hierarchies anywhere in the project, whereas a sealed class should contain all the hierarchies in the same file.
- Another important advantage of using a sealed class over abstract class is that it helps the IDE to understand the different types involved and thereby helps the user in auto-filling and avoiding spell mistakes.

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
  of scope scope end all the coroutine launch with in the scope are not completed yet will be
  cancelled.
- Coroutine started in the same scope form a hierarchy.
- A parent Job won't complete until all the child job get completed.
- Cancelling a parent job will cancel all the child.
- Cancelling a child will not cancel teh parent.
- **Ordinary JOB** if a child coroutine get failed , exception is propagated upwards and cancel all
  its jobs.
- **Supervise JOB** if a child coroutine get failed , exception is not propagated upwards and do not
  cancel all its jobs.
    - The `SupervisorJob` allows you to define a hierarchy of coroutines where the failure or
      cancellation of one coroutine does not affect the others.

#### Suspending function

- Suspending function , A function with a `suspend` modifier called suspended function.
    - It is call from another suspended function or coroutine scope.
    - It can not be call from normal function.
    - We can call the normal function fro inside a coroutine.

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
  that makes a coroutine coopera tive.
- `job.cancelAndJoin()` , it will cancel the coroutine if coroutine is cooperative , else wait to
  finish the coroutine.
- `CoroutineScope.isActive()` to check the coroutine is active or not.

### Coroutine Exception Handling

- We can handle by `try{ } catch{} throw CancellationException`. Any function that is cancelable
  from coroutine package will throw the `CancellationException` , when it got cancelled.
- We can not run a `suspended` function from the `finally{}` block.
- If we want to execute some `suspend` function from `finally{}` block than wran the code
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

- Every coroutine has a coroutine context.

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
            delay(10)
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
                delay(40)
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
  will throw the `CancelationException` so wee ned catch that exception specifically. or we need to
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
        // this code will be called because we cath the CancellationException so that will not perform its cancellation.
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

  
