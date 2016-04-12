package be.vergauwen.simon.androidtestingexample.core.di

import android.content.Context
import be.vergauwen.simon.androidtestingexample.core.rx.Transformers
import be.vergauwen.simon.androidtestingexample.core.service.GithubAPI
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class,ServiceModule::class))
interface ApplicationComponent {
  val applicationContext: Context
  val transformers : Transformers
  val githubAPI : GithubAPI
}