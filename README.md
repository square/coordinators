Coordinators
============

Simple lifecycle for your MVWhatever on Android. No kidding.

![lifecycle](lifecycle.png)

```java
class ExampleCoordinator extends Coordinator {

  @Override public void attach(View view) {
    // Attach listeners, load state, whatever.
  }

  @Override public void detach(View view) {
    // Unbind listeners, save state, go nuts.
  }
}
```

```java
// Create a factory for your Coordinators.
CoordinatorProvider coordinatorProvider; // @Nullable (View) -> Coordinator

// Bind a Coordinator to a View.
Coordinators.bind(view, coordinatorProvider);

// Bind a Coordinator to any child View added to a group.
Coordinators.installBinder(viewGroup, coordinatorProvider);
```

Download
--------

```groovy
dependencies {
  api 'com.squareup.coordinators:coordinators:0.4'
}
```

License
-------

    Copyright 2016 Square

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

