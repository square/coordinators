Change Log
==========

Version 0.4 *(2018-03-26)*
--------------------------
* Protects against reentrant calls to `onViewDetachedFromWindow`.
* Changes contract of `isAttached()`, which is now false when `detach()` is called.

Version 0.3 *(2017-04-28)*
--------------------------
* `installBinder` now binds existing children of the target ViewGroup. (@saantiaguilera)
* Fixes call to `attach` before View is attached to Window.
* Various sample app improvements, unrelated to their use of Coordinators.

Version 0.2 *(2016-11-30)*
--------------------------
Bump for external release. No difference from 0.1.

Version 0.1 *(2016-08-12)*
--------------------------
Initial, internal release.
