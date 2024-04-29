## Android interview Question and Answers

1. Difference between Serializable vs Parcelable

`Old way Serializable `

- it is an interface jy=ut need to implement on the object and rest is done by itself. but it is not
  flexible and taking more memory and also use the reflection internally.

`Parcelable`

- Android Related Api and designe dfor Android specific. it has more control we can customized the
  object and tell the object which part of the object need to be serialized.
- `Parcelable interface` and override methods such as` writeToParcel() and createFromParcel()`
-

2. What is the remember in compose?

- It is used to hold and remember the state of UI, it will remember the old state of the Data if
  that one is not changed then recomposition is not happen if changed then recomposition happen.
- for example if we use `TextFiled(value = name, onValueChanged{name=it})` if we do not remember the
  state of name than =it will not recompose the compose and we did not see anything on the screen
  after update name.

3. what is the rememberSavable in compose?
   `ANS:` Use to survive the configuration changes in compose.
4. what is recomposition in compose and how it will happen?
   `Ans:` Once the UI draw it can not be changed coz it is immutable , if we want to chage the UI
   then we need to change the State of UI that is responsible for change the state of UI using data.
   so when we have changed any data in the state the Ui will be recomposed.
5. what is launchEffect in compose where you will use it?
    - inside the composeScope for loadingData , do background and time consuming tasks.
6. Difference coroutineScope of launchEffect.

- `coroutineScope`
    - you're responsible for cancelling the coroutines launched within its scope if needed.
    - can be used for launching coroutines in any Kotlin code.
- `LaunchedEffect(){}`
    - automatically cancels the coroutine when the effect is removed or when the key changes, which
      is useful for managing the lifecycle of coroutines in Compose components
    - is specifically designed for use in Jetpack Compose components to handle side effects related
      to UI composition.

7. How launchEffect and coroutineScope behave in case of minimize the app?

`When the app is minimized or not visible:`

- If the app is minimized or not visible, the coroutine launched within the LaunchedEffect will
  continue to run. This behavior is consistent with how coroutines operate in general. They are not
  directly tied to
  the visibility of the UI.

`When the app is visible:`

- If the app is visible, the coroutine launched within the LaunchedEffect will also continue to run.
  Compose UI doesn't automatically pause or cancel coroutines when the app's UI is visible.

It's important to note that Jetpack Compose itself doesn't have specific features for handling app
lifecycle events like minimizing or restoring, unlike traditional Android activities or fragments.
However, you can use Android's lifecycle-aware components, such as LifecycleObserver or
LifecycleCoroutineScope, in conjunction with Compose to handle lifecycle events and manage
coroutines accordingly if needed.

9. In case a part of your UI is complex and cannot be achieved by using Jetpack Compose, how would
   you integrate XML into your Compose-based UI?
   `Ans:` -> using `AndroidView{ factory->(context){},update{->View}}`
10. What is Cold and Hot Flow , Realtime example of hot flow and cold flow and their use-cases and
    difference between them.

- `FLow`-> cold flow , if no collector it will not emmit , when ever the collector attached it itr
  will collect from start.

## Usecases

- fetching data from remote or database , kind of datasource.
- Performing a sequence of transformations or computations on a set of input data

`- StateFlow , SharedFlow ->` hot flow. A hot flow starts emitting items as soon as it's created,
regardless of whether there are any collectors attached to it.

## Usecases

- Sharing state or data updates between different parts of the application, such as UI components
  and background tasks.
- Broadcasting messages or notifications to multiple subscribers simultaneously

13. How would you expose ViewModel state to Jetpack Compose, and what approach would you take to
    collect ViewModel state and flows within a Composable function.
    `StateFlow<Data>->it will collect in Ui using collectAsLifeCycleState()`
14. How you will achieve data flow with api call to ui layer->StateFlow(ViewModel)->UseCase->
    Repository->Data
15. How you will call api and implement in clean architecture -> MVVM/MVI
16. what is the scope in coroutines and different between all scope
    `runBlockingScope{},GlobalScope{} ,launch{},asyncAwait{}, coroutioneScope{}`
17. Questions on viewModelScope and configuration changes.
    `Ans:`  ViewModelScope -> explained below , How it survive configuration changes -> Internal
    working of
    viewModel.
18. How do you ensure that UI data is preserved and can be displayed again in your application if
    the operating system kills your app's process?

- use `saveStateHandler in viewModel` on the it is bundle and saved data in bundle so you can save
  and
  get the data after process death.
- it is useful to survive the process death.

19. diffrent between state flow and shared flow and usecases of both of theme.

`Ans:` -> When we want to hold the latest stae use stateFlow.

- When we want to share the same flow between multiple collector use sharedFlow ,use case errorShow
  it will not trigger again and again.

20. what all think take care while you are going to develop application
    `Ans:`  Architecture , Screen orientation and screen sizes , Dark and light theme support,
    Security , Data store policy , Https network configuration , certificate pinning and all.

21. how viewModel scope and lifecycle scope behave when you minmize the app

### ViewModel Scope:

- ViewModels are scoped to the lifecycle of the associated UI component (e.g., Activity or
  Fragment). When the UI component is destroyed due to being minimized or otherwise, the
  `ViewModel remains active until the associated lifecycle is finished.`
- When the app is minimized, and the associated UI components (e.g., Activities or Fragments)
  are paused or stopped, the ViewModels associated with these components remain in memory.
- As long as the process containing the app remains alive (which is usually the case unless the
  system needs to reclaim resources), the ViewModel instances will be retained.
- `This allows ViewModels to preserve their state across configuration changes and lifecycle
  events, ensuring that data is not lost when the app is minimized and restored.`

### Lifecycle Scope

- The lifecycle scope is tied to the lifecycle of the UI component it's launched from. For example,
  if you launch a coroutine using lifecycleScope.launch { ... } from an Activity or Fragment, the
  coroutine will automatically be cancelled when the associated UI component is destroyed or
  stopped.
- When the app is minimized and the associated UI components are paused or stopped, any coroutines
  launched within their lifecycle scope will be paused or cancelled, depending on the
  implementation.
- It will be useful when we want to implement something that is bind to some Ui components than we
  can use the the lifecycle scope or activity or Fragment so that this will fetch the background
  task or data until the view is visible.


22. How viewModel scope behave when you navigate to another activity

### If Activity Finished

- If we launch the ActivityB from ActivityB and ActivityA is destroyed then the viewModel associated
  with ActivityA is got destroyed or we can say onCleared() of viewModel will be called.

### If Activity is Retain in the Background

- If ActivityA is in BackStack and ActivityB is in foreground then the viewModel associated with
  ActivityA is still in memory and not cleared. coz ActivityA io not destroyed.
- When we launch the ActivityA from background to foreground all the data hold by viewmodel is
  holding the state of data.
- `As long as the activity remains in the background stack and its process is alive, the ViewModel
  associated with it will be retained`
- `The ViewModel maintains its state across configuration changes and lifecycle events.`

24. How you will define ui state in your project -> as DataClass
25. How you will define ui action -> SealedCLass

27. Apart SONAR which other tools you used? sonarQube and Detek (Need to check)
28. How you will find out memory leaks in app? ->?Memory Profiler and LeackCanery.
29. what will happen if there are memory leaks in your android app? -> hold the object for more time
    memory get increased. Will get ANR and crash, Performance Degradation, Battery Drain
30. What type of test you write one in your app? -> Unit
31. What Unit testing tools you have used? -> mock , mockWebServer
32. How you stub the responses?
33. How mock works?
34. How you will verify expected API response in unit testing? -> mockWebServer , FakeRepositoryImpl
    using mockito.
35. What you use for UI testing? -> Espresso
36. What is coroutines in android?
37. Coroutines sequential- and parallel execution?
38. Launch coroutine builder and async coroutine builder.
    `AnS` Launh return the `JOB` object and it will like file and forget and `Async{}.await()`  it
    will return `Deferred` it will like provide result in future.
39. Which main components of an Android app?
    `ANS` Activity , Service , BroadcastReceiver and ContentProvider
40. What is the use of Android Manifest file?
41. What you have you used for dependency injection? `hilt`
42. Lifecycle of Singleton scope and Activity scope in dagger? ->
    `Ans` Singleton will live for app , and Activity will be live for that activity only
50. Where we keep the schema for deeplink?
    Manifest -> we have schemas.
52. How to handle conflict?
    `Situation` -> find out the situation
    `Task` -> fins out the required task
    `Action` -> take action to resolve the conflicts, just like with clear and solid communication
    and statics. what every you are saying must be data driven.
    `Result` -> It should be positive result.


54. How CI/CD pipeline works?

- CI-> Continuous Integration
    - Check -> KLinh , Deteck etc
    - Build -> Looking for compile time error
    - Test -> Unit test , integration test , instrumented test
- Setup KLint and Detect in dependency
- Platform -> Jenkins , githubActions , CircleCI.
- We will Use GithubActions
    - Just need to tap on Action and search for thr Android it will provide you the default detup
      for the android with `.yml` file
    - it will create the folder in folder `MyApplication/.github/worksflows/android.yml`
    - It define the name , on -> triggers and jobs , machine on which it run and flows.
- `CD -> Continuous Delivery / Deployment`
- It is used to reduce the deployment time.
- We need to integrate the `distribution with firebase and githubAction`.
    - Just need to use the firebase assistance to enable the distribution
    - Now set up github action and fcm token in github settings to access them.
    - Set the users , groups via email.
    - Now we need to add the distributionActions.yml file
    - Now we hava made some changes in build.gradle file for distributing app.
    - Now we run the distribution action file and get the invitation in the selected group.
    - They will download the app and test.

55. Did you write script for CI/CD?

- GitHubAction need to check and write the steps here

56. What is A/B testing?

### 57. What are the benifits of A/B testing?

- Gause work can be done , We will get which version is most effective liked by audience.
- A/B testing is a process of analyzing the feature and its data before rolling it completely. We
  have created some variant on the basis of some different feature and check with the current
  audience that which one is more adapted by the audience.
- We have done this using `Firebase`

1. create remote config for ru A/B testing -> preparing for A/B Test
2. create an experiment -> Name , description , app -> id and decide audiences. create the variant
   for testing and goal. some default matrix are added by default just like daily engagement ,
   retention , and crash and analytics of the app.
    - Just like we hae a unique accessToken for FCM when our app is running so we will get the info
      that with accession is using which variant of application.
3. understanding experiment result

- check matrix and variant and find out the winner.

4. Ab Testing with Notification Too

- using FCM, Notification Also.

### 59. Difference between sealed interface and Enum

Ands: sealed interface is not searilazed while Enum is.

### 59. Difference between MVVM and MVI

Ans: - MVI has race condition while MVVM has not , we can fix it using MVVM + MVI. using
stateFlow.update{} this one is thread safe.

58. What is unidirection data flow in compose
59. What is jetpack compose?
60. What is composables?
61. Advanteges of jetpack compose?
62. AB testing and server driven UI.
63. GraphQL ab testing.
64. How you will implement deeplink?
65. What changes you do in Android to implement deeplink?
66. Which static code analysis tools you have used? link , kLink
67. What is GraphQL and its benifits?
68. What is the difference between merge and rebase?