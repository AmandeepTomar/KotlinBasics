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
merge(flow1,flow2).onEach{
    
}
```

Channel

- if we have just one collector than we should use the channel.