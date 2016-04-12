package be.vergauwen.simon.androidtestingexample

import android.app.Application
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationModule
import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.ServiceModule

class ExampleApp : Application(){
  companion object {
    lateinit var component: ApplicationComponent
  }

  override fun onCreate() {
    super.onCreate()

    component = DaggerApplicationComponent.builder()
        .serviceModule(ServiceModule())
        .applicationModule(ApplicationModule(this))
        .build()
  }
}