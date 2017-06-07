package com.squareup.coordinators.sample.container;

import com.squareup.coordinators.sample.container.counter.Counter;
import com.squareup.coordinators.sample.container.counter.CounterCoordinator;
import com.squareup.coordinators.sample.container.tictactoe.TicTacToeBoard;
import com.squareup.coordinators.sample.container.tictactoe.TicTacToeCoordinator;

/**
 * All the objects. In real life might be generated, e.g. by Dagger.
 */
class ObjectGraph {
  private final Counter counter = new Counter();
  private final TicTacToeBoard ticTacToe = new TicTacToeBoard();

  private CounterCoordinator counterCoordinator() {
    return new CounterCoordinator(counter);
  }

  private TicTacToeCoordinator ticTacToeCoordinator() {
    return new TicTacToeCoordinator(ticTacToe);
  }

  @SuppressWarnings("unchecked") //
  <T> T create(String className) {
    if (TicTacToeCoordinator.class.getName().equals(className)) {
      return (T) ticTacToeCoordinator();
    }

    if (CounterCoordinator.class.getName().equals(className)) {
      return (T) counterCoordinator();
    }

    throw new IllegalArgumentException("Unknown class: " + className);
  }
}
