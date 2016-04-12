package be.vergauwen.simon.androidtestingexample.ui

import android.util.Log
import be.vergauwen.simon.androidtestingexample.ExampleApp
import be.vergauwen.simon.himurakotlin.MVPDaggerActivity

class ExampleActivity : MVPDaggerActivity<ExampleContract.View, ExamplePresenter, ExampleComponent>(), ExampleContract.View {

  override fun createComponent(): ExampleComponent =
      DaggerExampleComponent.builder().applicationComponent(ExampleApp.component).build()

  override fun printRepo(repoName: String) {
    Log.e("ExampleActivity", repoName)
  }

  override fun showError() {
    Log.e("ExampleActivity", "showError(")
  }
}