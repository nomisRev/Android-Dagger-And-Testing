package be.vergauwen.simon.androidtestingexample.core.di

import be.vergauwen.simon.androidtestingexample.core.service.GithubAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ServiceModule {

  val URI = "https://api.github.com"

  @ApplicationScope
  @Provides
  fun provideRestAdapter(): Retrofit {
    return Retrofit.Builder().baseUrl(URI).addConverterFactory(
        GsonConverterFactory.create()).addCallAdapterFactory(
        RxJavaCallAdapterFactory.create()).build()
  }

  @ApplicationScope
  @Provides
  fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
    return retrofit.create(GithubAPI::class.java)
  }
}