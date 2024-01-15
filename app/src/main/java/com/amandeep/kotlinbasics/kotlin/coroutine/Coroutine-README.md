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


