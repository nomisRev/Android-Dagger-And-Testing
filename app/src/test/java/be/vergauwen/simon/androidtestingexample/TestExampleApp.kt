//package be.vergauwen.simon.androidtestingexample
//
//import android.app.Application
//import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent
//import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent
//import be.vergauwen.simon.androidtestingexample.core.di.TestApplicationModule
//import be.vergauwen.simon.androidtestingexample.core.di.TestServiceModule
//
//class TestExampleAppImpl : Application() {
//  companion object {
//    lateinit var component: ApplicationComponent
//  }
//
//  override fun onCreate() {
//    super.onCreate()
//
//    component = DaggerApplicationComponent.builder().applicationModule(
//        TestApplicationModule(this)).serviceModule(TestServiceModule()).build()
//  }
//}