/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.coordinators.sample.container;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;

public class ContainerSampleActivity extends Activity {
  private static final ObjectGraph OBJECT_GRAPH = new ObjectGraph();

  private static @LayoutRes int currentScreen;

  private ViewGroup container;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.root_layout);
    container = (ViewGroup) findViewById(R.id.container);

    // Make each button show the layout identified in its tag. See root_layout.xml

    ViewGroup buttons = (ViewGroup) findViewById(R.id.buttons);
    for (int i = 0; i < buttons.getChildCount(); i++) {
      View button = buttons.getChildAt(i);

      String layoutName = (String) button.getTag(R.id.layout_to_show_tag);
      layoutName = layoutName.substring("res/layout/".length());
      layoutName = layoutName.substring(0, layoutName.length() - ".xml".length());

      final int layout = getResources().getIdentifier(layoutName, "layout", getPackageName());

      button.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          showScreen(layout);
        }
      });
    }

    // Install a binder that will be called as each child view is added to the container
    // layout. Our binder expects each view to have a tag with the classname of the coordinator
    // that knows how to drive it. Again, we're setting those tags in the layout files themselves,
    // BECAUSE WE CAN!

    Coordinators.installBinder(container, new CoordinatorProvider() {
      @Nullable @Override public Coordinator provideCoordinator(View view) {
        String coordinatorName = (String) view.getTag(R.id.coordinator_class_tag);
        return OBJECT_GRAPH.create(coordinatorName);
      }
    });

    refresh();
  }

  private void showScreen(@LayoutRes int id) {
    currentScreen = id;
    refresh();
  }

  private void refresh() {
    container.removeAllViews();
    if (currentScreen > 0) {
      LayoutInflater.from(container.getContext()).inflate(currentScreen, container);
    }
  }
}
