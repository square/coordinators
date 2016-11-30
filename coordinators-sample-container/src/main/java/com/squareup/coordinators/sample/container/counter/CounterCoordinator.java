package com.squareup.coordinators.sample.container.counter;

import android.view.View;
import android.widget.TextView;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.sample.container.R;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CounterCoordinator extends Coordinator {
  private final Counter counter;

  private Disposable subscription = null;

  public CounterCoordinator(Counter counter) {
    this.counter = counter;
  }

  @Override public void attach(final View view) {
    super.attach(view);

    final TextView textView = (TextView) view.findViewById(R.id.counter_value);
    final View up = view.findViewById(R.id.count_up);
    final View down = view.findViewById(R.id.count_down);

    subscription = counter.count().subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer count) throws Exception {
        textView.setText(String.format("%s", count));
      }
    });

    up.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        counter.up();
      }
    });

    down.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        counter.down();
      }
    });
  }

  @Override public void detach(View view) {
    subscription.dispose();
    super.detach(view);
  }
}
