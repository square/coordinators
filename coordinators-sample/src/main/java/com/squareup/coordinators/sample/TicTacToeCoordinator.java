package com.squareup.coordinators.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.sample.TicTacToeBoard.Value;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Binds a {@link ViewGroup} with nine TextView children to a
 * {@link TicTacToeBoard}.
 */
public class TicTacToeCoordinator extends Coordinator {
  private final TicTacToeBoard board;

  public TicTacToeCoordinator(TicTacToeBoard board) {
    this.board = board;
  }

  @Override public void attach(View view) {
    super.attach(view);

    final ViewGroup viewGroup = (ViewGroup) view;
    view.setTag(R.id.tic_tac_toe_sub_tag, board.grid().subscribe(new Consumer<Value[][]>() {
      @Override public void accept(Value[][] values) throws Exception {
        refresh(viewGroup, values);
      }
    }));

    for (int i = 0; i < 9; i++) {
      final int index = i;
      final View cell = viewGroup.getChildAt(i);

      cell.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          toggle(cell, index);
        }
      });
    }
  }

  @Override public void detach(View view) {
    ((Disposable) view.getTag(R.id.tic_tac_toe_sub_tag)).dispose();
    view.setTag(R.id.tic_tac_toe_sub_tag, null);

    super.detach(view);
  }

  private void refresh(ViewGroup viewGroup, Value[][] values) {
    for (int i = 0; i < 9; i++) {
      int row = i / 3;
      int col = i % 3;

      TextView cell = (TextView) (viewGroup).getChildAt(i);
      Value value = values[row][col];
      cell.setText(value.text);
      cell.setTag(R.id.tic_tac_toe_cell_value_tag, value);
    }
  }

  private void toggle(View cell, int index) {
    Value oldValue = (Value) cell.getTag(R.id.tic_tac_toe_cell_value_tag);
    Value[] values = Value.values();
    Value newValue = values[(oldValue.ordinal() + 1) % values.length];

    int row = index / 3;
    int col = index % 3;

    board.set(row, col, newValue);
  }
}
