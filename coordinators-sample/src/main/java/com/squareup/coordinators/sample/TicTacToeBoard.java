package com.squareup.coordinators.sample;

import android.os.Looper;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import io.reactivex.functions.BiPredicate;
import java.util.Arrays;

import static com.squareup.coordinators.sample.TicTacToeBoard.Value.EMPTY;
import static java.lang.String.format;

class TicTacToeBoard {
  enum Value {
    EMPTY(""), X, O;

    final String text;

    Value() {
      this.text = name();
    }

    Value(String text) {
      this.text = text;
    }
  }

  private BehaviorRelay<Value[][]> grid = BehaviorRelay.createDefault(new Value[][] {
      { EMPTY, EMPTY, EMPTY }, { EMPTY, EMPTY, EMPTY }, { EMPTY, EMPTY, EMPTY }
  });

  /**
   * Returns an observable of the tic tac toe board. First value is provided immediately,
   * succeeding values are guaranteed to be distinct from previous values. Values are
   * always provided from the main thread.
   */
  public Observable<Value[][]> grid() {
    return grid.distinctUntilChanged(new BiPredicate<Value[][], Value[][]>() {
      @Override public boolean test(Value[][] a, Value[][] b) throws Exception {
        return Arrays.equals(a, b);
      }
    });
  }

  public void set(int row, int col, Value value) {
    assertOnMain();
    assertIndex("row", row);
    assertIndex("col", col);

    Value[][] newGrid = new Value[3][];
    for (int i = 0; i < 3; i++) {
      newGrid[i] = grid.getValue()[i].clone();
    }

    newGrid[row][col] = value;
    grid.accept(newGrid);
  }

  private void assertIndex(String label, int index) {
    if (index < 0 || index > 2) {
      throw new IllegalArgumentException(format("%s must be 0, 1 or 2", label));
    }
  }

  private void assertOnMain() {
    // https://speakerdeck.com/rjrjr/where-the-reactive-rubber-meets-the-road

    if (!(Looper.getMainLooper().getThread() == Thread.currentThread())) {
      throw new AssertionError("Model updates must come from main thread.");
    }
  }
}
