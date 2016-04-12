package be.vergauwen.simon.androidtestingexample.core.rx

import rx.Observable
import rx.schedulers.Schedulers

class MockRxUtil : Transformers {
  override fun <T> applyComputationSchedulers(): Observable.Transformer<T, T> =
      Observable.Transformer<T, T> {
        it.subscribeOn(Schedulers.immediate())
            .observeOn(Schedulers.immediate())
      }

  override fun <T> applyIOSchedulers(): Observable.Transformer<T, T> =
      Observable.Transformer<T, T> {
        it.subscribeOn(Schedulers.immediate())
            .observeOn(Schedulers.immediate())
      }
}