# Kotlin Basics 
- Arrays
- Sealed class and interfaces
- Mutable and immutable casses
- Strings
- Val , var , const , lamda , highorder function. 


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

  
