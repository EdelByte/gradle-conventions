# Maintainer Guide

Setup and release notes for maintaining this repository. These files are maintainer-facing, not private; do not put credentials here.

The numbered findings live in [todo.md](todo.md). All commands below run from the repository root unless a subshell explicitly changes directories.

## Local Setup

Install Git and a full JDK 17, including `javac`, using your preferred package manager, JDK manager, or IDE. Select it through your environment or IDE's Gradle JVM setting. No particular SDK manager is required.

Use the checked-in Gradle wrapper; a separate Gradle installation is unnecessary. It currently selects Gradle 8.14.4, which does not support running on Java 25. Internet access is needed for the initial Gradle and dependency downloads.

```bash
java -version
javac -version
./gradlew --version
./gradlew clean build --no-daemon
```

If Gradle selects the wrong Java installation, point `JAVA_HOME` at a full JDK 17 and put its `bin` directory on `PATH`, or adjust the IDE's Gradle JVM. Do not commit machine-specific Java paths.

## Build and Validation

`build` compiles and packages the convention plugins and runs `validatePlugins` and the standard test task. Artifacts are written to `build/libs/`.

There are currently no automated test sources, so a successful build does not verify consumer test discovery or plugin behavior end to end. Gradle TestKit coverage is tracked in item 1 of [the findings](todo.md).

```bash
./gradlew build --no-daemon
./gradlew validatePlugins --no-daemon
```

Configuration cache and build caching are enabled in [gradle.properties](../../gradle.properties). Repeating the same build command should reuse the configuration cache.

The [CI workflow](../../.github/workflows/ci.yml) builds on pushes to `main` and pull requests, and can be dispatched manually. It does not create releases or publish plugins.

## Version and Release Configuration

[gradle.properties](../../gradle.properties) is the single project version source. Development versions use `-SNAPSHOT`; all seven convention plugins share the project version.

[build.gradle.kts](../../build.gradle.kts) applies ResearchGate's `net.researchgate.release` plugin to this repository's build only. It is not applied to projects consuming the convention plugins.

The release configuration:

- Requires branch `main`, a clean working tree, and no untracked files or incoming/outgoing commits.
- Rejects snapshot dependencies.
- Runs `clean` and `build` for the release version.
- Commits changes to the version file only.
- Creates annotated tags in the form `v0.3.1`.
- Pushes release/version commits and the tag to `origin`.
- Advances the version to the next development snapshot.

Releases and Plugin Portal publishing are separate operations. Neither runs as part of a normal build.

## Cut a Release

First commit and push all intended changes, including release configuration and documentation changes. Verify that your Git identity and authentication can commit and push to `origin`; the release plugin does both. Branch protection must permit the release commits, otherwise this workflow will fail.

Update `main` and check the working tree:

```bash
git switch main &&
git fetch origin --tags &&
git pull --ff-only origin main &&
git --no-pager status --short --branch &&
./gradlew clean build --no-daemon
```

Choose an unused release version. The examples below release `0.3.1` and advance development to `0.3.2-SNAPSHOT`; replace these versions consistently for future releases.

To inspect existing tags:

```bash
git --no-pager tag --list
```

**The following command changes the version file, creates commits and a tag, and pushes to `origin`.** It does not publish plugins to the Plugin Portal.

```bash
./gradlew release --no-daemon
```

Confirm the release version and next snapshot at the prompts. Alternatively, provide both explicitly for a non-interactive release:

```bash
./gradlew release --no-daemon \
  -Prelease.useAutomaticVersion=true \
  -Prelease.releaseVersion=0.3.1 \
  -Prelease.newVersion=0.3.2-SNAPSHOT
```

Use either the interactive or non-interactive command, not both. After completion, `main` should contain the next snapshot version, while `v0.3.1` points to the release commit.

```bash
git --no-pager show v0.3.1:gradle.properties
git --no-pager status --short --branch
git ls-remote --heads --tags origin
```

## Publish to the Gradle Plugin Portal

Use an account with permission to publish the `sh.edelbyte.gradle.*` plugin IDs. Obtain credentials from the Plugin Portal's API Keys page.

Keep credentials outside the repository:

- Copy the Portal-provided `gradle.publish.key` and `gradle.publish.secret` properties into your user-level `~/.gradle/gradle.properties` (or the file in your custom `GRADLE_USER_HOME`). Restrict access to that file.
- Alternatively, inject `GRADLE_PUBLISH_KEY` and `GRADLE_PUBLISH_SECRET` through your secret manager or CI secret store.
- Do not put credentials in the repository's `gradle.properties`, documentation, or command-line arguments.

**Publish the tagged release, not the next snapshot on `main`.** Create a separate detached worktree; use an unused directory and substitute the version you actually released:

```bash
git worktree add --detach ../gradle-conventions-release-v0.3.1 v0.3.1 && (
  cd ../gradle-conventions-release-v0.3.1 &&
  git --no-pager describe --tags --exact-match HEAD &&
  git --no-pager show HEAD:gradle.properties &&
  git --no-pager status --short &&
  ./gradlew clean build --no-daemon &&
  ./gradlew publishPlugins --validate-only --no-daemon
)
```

Check that the version has no `-SNAPSHOT` suffix, the worktree is clean, and validation succeeds. `--validate-only` checks the publication without uploading it; it is not a substitute for verifying a real publication.

When ready, this separate command uploads the plugins:

```bash
(
  cd ../gradle-conventions-release-v0.3.1 &&
  ./gradlew publishPlugins --no-daemon
)
```

Confirm all seven plugin IDs and the released version on the Plugin Portal, accounting for any approval requirements, then verify resolution in a consumer project. Once no longer needed, remove the publishing worktree without forcing removal of local changes:

```bash
git worktree remove ../gradle-conventions-release-v0.3.1
```

## If Something Fails

- For a dirty-tree, untracked-file, or ahead/behind error, resolve the underlying Git state instead of disabling the release checks.
- For Git authentication failures, fix access to the configured `origin`. Git credentials and Plugin Portal credentials are independent.
- Release operations are not atomic. If a release fails, inspect local and remote branches/tags and the version file before retrying. Some commits or tags may already have been pushed.
- If publication fails after the Git release succeeds, keep the release tag and retry publishing from that same tag after checking which plugins or artifacts were already accepted. Do not create a second release just to retry an upload or assume an already published version can be overwritten.
- The release plugin currently emits a `StartParameter.settingsFile` deprecation warning on Gradle 8.14.4. Review plugin compatibility before upgrading the wrapper to Gradle 9.

Builds, plugin validation, and configuration-cache reuse have been checked locally. A real release and Plugin Portal publication have not yet been exercised as part of this setup.

## References

- [ResearchGate release plugin](https://github.com/researchgate/gradle-release)
- [Gradle Plugin Portal publishing guide](https://plugins.gradle.org/docs/publish-plugin)
