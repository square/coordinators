package com.squareup.coordinators.sample.container.counter;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

public class Counter {
  private BehaviorRelay<Integer> count = BehaviorRelay.createDefault(0);

  public Observable<Integer> count() {
    return count;
  }

  public void up() {
    count.accept(count.getValue() + 1);
  }

  public void down() {
    count.accept(count.getValue() - 1);
  }
}
