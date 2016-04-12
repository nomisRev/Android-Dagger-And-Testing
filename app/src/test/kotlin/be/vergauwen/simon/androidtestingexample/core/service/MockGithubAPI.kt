package be.vergauwen.simon.androidtestingexample.core.service

import be.vergauwen.simon.androidtestingexample.core.model.GitHubRepo
import rx.Observable

class MockGithubAPI(private val repos: List<GitHubRepo>) : GithubAPI {

  var throwError = false

  override fun getRepos(): Observable<List<GitHubRepo>> {
    return if (throwError) Observable.error(Exception("exception")) else Observable.just(repos)
  }
}