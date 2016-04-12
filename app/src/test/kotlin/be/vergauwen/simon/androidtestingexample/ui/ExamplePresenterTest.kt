package be.vergauwen.simon.androidtestingexample.ui

import be.vergauwen.simon.androidtestingexample.core.model.GitHubRepo
import be.vergauwen.simon.androidtestingexample.core.rx.MockRxUtil
import be.vergauwen.simon.androidtestingexample.core.service.MockGithubAPI
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExamplePresenterTest {

  lateinit var presenter: ExamplePresenter
  lateinit var view: MockExampleView
  val githubRepo = listOf(GitHubRepo("test", "www.test.com", "test_desc"))
  lateinit var githubAPI : MockGithubAPI

  @Before
  fun setUp() {
    githubAPI = MockGithubAPI(githubRepo)
    presenter = ExamplePresenter(githubAPI, MockRxUtil())
    view = MockExampleView()
  }

  @Test
  fun preConditions() {
    assertNotNull(presenter)
    assertFalse(view.printedRepo)
  }

  @Test
  fun testAssertViewAttached() {
    presenter.attachView(view)
    assertNotNull(presenter.getView())
  }

  @Test
  fun testRepoPrinted() {
    presenter.attachView(view)
    assertFalse(view.printedRepo)
    presenter.getRepos()
    assertTrue(view.printedRepo)
  }

  @Test
  fun testRetrofitError() {
    presenter.attachView(view)
    assertFalse(view.errorShown)
    githubAPI.throwError = true
    presenter.getRepos()
    assertTrue(view.errorShown)
  }
}

