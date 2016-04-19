# Android Dagger and testing example

* Testing with MVP , Dagger 2 and Retrofit 2
* Required knowledge: MVP  and Retrofit 2.

**I noticed that some people on mac have problems running the tests, if so follow these notes to fix the problem.**
https://github.com/robolectric/robolectric/wiki/Running-tests-in-Android-Studio

## Dagger 2 (Dependency Injection)

* **Dependency**: If A is dependent on B, that means A cannot function without B. So dependency injection means that when A is dependent on B, we will inject B into A so that A can function correctly.

* So Dependency injection is the pattern of the injection of a dependency to a dependent object (a client) that needs it.

* Before you start using dagger, it's important you understand the concepts of dependency injection.
* Now how exactly does this work. And what does it look like in Android.
 <img src="/DI-pattern.png" alt="Dependency pattern">

* The Application has a Component aka container, the component knows the dependencies and knows where to get them (from modules). So when a dependency must be injected, the component gets the dependencies from the module.
* This is where the beauty of dependency injection happens! Let's say a class Example needs a DB, we can aggregate the actual implementation behind an interface. Our class Example is not interessted in how our DB works anyway, it only wants to query some data.
```
interface DB {
    Data querySomeData(int id);
}
```
```
class SQLite implements DB {
    @Override
    Data querySomeData(int id){
        //SQLite implementation
    }
}
```
* So let's say we were using SQLite but we want to use a different system like Realm, as shown below we can simply create a Realm implementation that exposes the same functionality trough our DB interface and have our module provide the Realm implementation instead of our SQLite implementation. And no changes in code have to be made anywhere else!!!
```
class RealmImpl implements DB {
    @Override
    Data querySomeData(int id){
        //Realm implementation
    }
}
```
```
class DBModule{
     @ApplicationScope
     @Provides
      DB provideDB(){
       return RealmImpl();
     }
}
```

* There are 3 types of injection:
  * Constructor injection
 ```
 class Example{
    DB dependency
    Example(DB dependency){
      this.dependency = dependency;
    }
    
    // ...
 }
   ```
  * Setter/method injection
 ```
 class Example{
    DB dependency
    void setDB(DB dependency){
      this.dependency = dependency;
    }
    
    // ...
 }
 ```
  * Property/field injection
 ```
 class Example{
     DB dependency
 }
  
 example.dependency = dependency;
 ```

* Simply, the `Application` has a property `Component`. This component (interface) exposes dependencies, for example `GithubAPI`. The Component relies on `Modules` in order to **provide** these dependencies. In our example we have a `ServiceModule` which creates and provides a GithubAPI to our dependent classes.

 * In code it looks like this:
 ```
 @ApplicationScope
 @Component(modules = arrayOf(ApplicationModule::class,ServiceModule::class))
 interface ApplicationComponent {
     val applicationContext: Context
     val transformers : Transformers
     val githubAPI : GithubAPI
 }
 ```
 ```
@Module
open class ServiceModule {

     val URI = "https://api.github.com"

     @ApplicationScope
     @Provides
     open fun provideRestAdapter(): Retrofit {
       return Retrofit.Builder().baseUrl(URI).addConverterFactory(
           GsonConverterFactory.create()).addCallAdapterFactory(
           RxJavaCallAdapterFactory.create()).build()
     }

     @ApplicationScope
     @Provides
      open fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
       return retrofit.create(GithubAPI::class.java)
     }
}
 ```
 ```
  open class ExampleApp : Application() {
   val component by lazy { createComponent() }

   @Override
   open fun createComponent(): ApplicationComponent =
       DaggerApplicationComponent.builder()
           .serviceModule(ServiceModule())
           .applicationModule(ApplicationModule(this))
           .build()
 }
 ```
 
 * As you can see, we have an `interface ApplicationComponent` which has `ServiceModule` as module ` @Component(modules = arrayOf(ApplicationModule::class,ServiceModule::class))` to **provide** GithubAPI, notice that in `ServiceModule` the method `fun provideGithubAPI(retrofit: Retrofit): GithubAPI` is annotated with `@Provides`.
 * Now we can create a class `Activity` that is depedent on `ApplicationComponent`, and you can inject one of the dependencies into this class using one of the three methods explained above annotating it with `@Inject`.
 
## Testing

 <img src="/DI-testing.png" alt="Dependency injection testing">

* As explained above we can replace our `Module`, so that instead of the production dependency it provides a mocked dependency. All our dependencies are injected through our `Application`. So we create a test application, override the `createComponent() : ApplicationComponent` function and specify our `TestModule` instead of our `ServiceModule`.
     *  If you want Robolectric to run a different `Application` than defined in your manifest, it will do it automatically for you when you add `Test` as a prefix to your `Application` name. (`TestExampleApp`)

* So let's do this for our GithubAPI
* First we'll create a `MockGithubAPI`:
```
class MockGithubAPI() : GithubAPI {
  val repos = listOf(GitHubRepo("test", "www.test.com", "test_desc"))
  var throwError = false

  override fun getRepos(): Observable<List<GitHubRepo>> {
    return if (throwError) Observable.error(Exception("exception")) else Observable.just(repos)
  }
}
```

* Now we can adjust our `ServiceModule` to provide an instance of MockGithubAPI instead of the retrofit instance of GithubAPI. 
```
@Module
class TestServiceModule : ServiceModule() {

  @ApplicationScope
  @Provides
  override fun provideRestAdapter(): Retrofit {
    return Retrofit.Builder().baseUrl(URI).addConverterFactory(
        GsonConverterFactory.create()).addCallAdapterFactory(
        RxJavaCallAdapterFactory.create()).build()
  }

  @ApplicationScope
  @Provides
  override  fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
    return MockGithubAPI()
  }
}
```

* As a final step you override the `Application` and you now succesfully replaced your depedencies with mocked dependencies.

```
class TestExampleApp : ExampleApp() {
  override fun createComponent(): ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(
      TestApplicationModule(this)).serviceModule(TestServiceModule()).build()
}
```

* You can use the same technique for other dependencies. In the source code of this example, you'll find a core.rx.RxUtil class that is used to inject `Transfomer` into the `Presenter`. You can mock these out to apply `Schedulers.immediate()` to keep the `AndroidSchedulers.mainThread()` out of your presenter unit test.

 <img src="/Test-coverage.png" alt="Test coverage">

* At the end of the day, this is what we like to see. So now you can go home and enjoy a drink.
