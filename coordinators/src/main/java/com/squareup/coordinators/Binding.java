/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.coordinators;

import android.support.annotation.NonNull;
import android.view.View;

final class Binding implements View.OnAttachStateChangeListener {
  private final Coordinator coordinator;
  private final View view;

  Binding(@NonNull Coordinator coordinator, @NonNull View view) {
    this.coordinator = coordinator;
    this.view = view;
  }

  @Override public void onViewAttachedToWindow(@NonNull View v) {
    if (v != view) {
      throw new AssertionError("Binding for view " + view
          + " notified of attachment of different view " + v);
    }
    if (coordinator.isAttached()) {
      throw new IllegalStateException(
          "Coordinator " + coordinator + " is already attached");
    }
    coordinator.setAttached(true);
    coordinator.attach(view);
    view.setTag(R.id.coordinator, coordinator);
  }

  @Override public void onViewDetachedFromWindow(@NonNull View v) {
    if (v != view) {
      throw new AssertionError("Binding for view " + view
          + " notified of detachment of different view " + v);
    }
    if (!coordinator.isAttached()) {
      // Android tries not to make reentrant calls, but doesn't always succeed.
      // See https://github.com/square/coordinators/issues/28
      return;
    }
    coordinator.setAttached(false);
    coordinator.detach(view);
    view.setTag(R.id.coordinator, null);
  }
}
