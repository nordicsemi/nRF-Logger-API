# Module log-timber

A [Timber](https://github.com/JakeWharton/timber) tree that forwards logs to the nRF Logger app.

An [nRFLoggerTree] forwards log messages to the
[nRF Logger](https://play.google.com/store/apps/details?id=no.nordicsemi.android.log) app
(or to a local content provider), so you can keep using Timber's familiar API while your logs end
up in an nRF Logger session. This module builds on the `log` module.

### Setup

The library is available on Maven Central and transitively includes the `log` module:

```kotlin
implementation("no.nordicsemi.android:log-timber:2.5.0")
```

### Usage

Plant an [nRFLoggerTree] as described on the [Timber](https://github.com/JakeWharton/timber)
website. Once planted, all `Timber` calls are written to the underlying log session:

```java
Timber.plant(new nRFLoggerTree(context, deviceAddress, deviceName));

Timber.i("Connected");
Timber.e(exception, "Connection failed");
```

The tree can be created from a device key/name, from an existing
[ILogSession][no.nordicsemi.android.log.ILogSession], or from a session `Uri`. Use
[nRFLoggerTree.newSession] to start a new session on an already-planted tree, and
[nRFLoggerTree.setLoggingTagsEnabled] to control whether tags are prefixed to messages.
If neither nRF Logger nor a local provider is available, the logs are ignored.

### Things to keep in mind

Timber's priorities do not map one-to-one onto nRF Logger's log levels:

1. This module uses `@NonNull` and `@Nullable` from *androidx.annotation* rather than the
   *org.jetbrains.annotations* ones that Timber uses.
2. Timber has no `APPLICATION` level, and its `DEBUG` has a higher priority than `VERBOSE`.
   As a result, entries logged through Timber's own methods (e.g. `Timber.i(...)`) can never be
   logged at the `APPLICATION` level.
3. `Log.VERBOSE` priority is mapped to the `VERBOSE` level (upgraded), and `Log.DEBUG` priority is
   mapped to the `DEBUG` level (downgraded).
4. By default the tag is prepended to the message as `[TAG] message`. Call
   `tree.setLoggingTagsEnabled(false)` to disable this (since 2.5).

# Package no.nordicsemi.android.log.timber

A `Timber.Tree` that writes messages to an nRF Logger session.

Contains [nRFLoggerTree], which uses the [Logger][no.nordicsemi.android.log.Logger] API from the
`log` module. Plant the tree with `Timber.plant(...)` and use Timber as usual; the tree translates each log call
into an nRF Logger entry, converting Timber's Android priorities into nRF Logger log levels.

# Package no.nordicsemi.android.log.timber.annotation

Type-safe `@IntDef` annotations used by this module.

[LogPriority] restricts a value to one of the standard `android.util.Log` priorities
(`VERBOSE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `ASSERT`) — the priorities Timber works with, before
they are translated into nRF Logger levels. The annotation is retained at source level only and
adds no runtime cost.
