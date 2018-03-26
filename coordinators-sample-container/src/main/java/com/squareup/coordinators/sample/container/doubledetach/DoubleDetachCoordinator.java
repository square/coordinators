package com.squareup.coordinators.sample.container.doubledetach;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.sample.container.R;

public class DoubleDetachCoordinator extends Coordinator {
  private int attachCount = 0;

  @Override public void attach(@NonNull View view) {
    attachCount++;
    super.attach(view);

    View button = view.findViewById(R.id.double_detach_button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Activity activity = (Activity) v.getContext();
        activity.finish();
      }
    });
  }

  @Override public void detach(@NonNull View view) {
    attachCount--;
    if (attachCount != 0) {
      // This would throw before https://github.com/square/coordinators/issues/28 was fixed.
      throw new AssertionError("Attach/detach imbalance! " + attachCount);
    }


    ViewGroup parent = (ViewGroup) view.getParent();
    parent.removeAllViews();

    super.detach(view);
  }
}
