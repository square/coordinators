Releasing
========

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 2. Update the `CHANGELOG.md` for the impending release.
 3. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 4. `./gradlew clean uploadArchives`.
 5. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.
 6. `git tag -a X.Y.X -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 7. Update the `gradle.properties` to the next SNAPSHOT version.
 8. `git commit -am "Prepare next development version."`
 9. `git push && git push --tags`

If step 4 or 5 fails, drop the Sonatype repo, fix the problem, commit, and start again at step 4.
