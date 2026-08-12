# Module log

An API for creating custom log entries viewable in the
[nRF Logger](https://play.google.com/store/apps/details?id=no.nordicsemi.android.log) app.

The library is used by nRF Connect and nRF Toolbox and is handy for debugging on devices where
LogCat is not available. Log sessions are grouped by day and by a caller-provided key, can be
marked and commented, and are opened for viewing directly in nRF Logger. When nRF Logger is not
installed, an app can keep a limited set of logs locally through a bundled content provider.

Your application needs the `no.nordicsemi.android.LOG` permission to write to nRF Logger.

### Setup

The library is available on Maven Central:

```kotlin
implementation("no.nordicsemi.android:log:<version>>")
```

If you use ProGuard/R8, the following rule is added automatically:

```
-keep class no.nordicsemi.android.log.** { *; }
```

### Creating a session and appending entries

A session must be created before entries can be appended. It takes a non-changeable `key`
(e.g. a device address, used by nRF Logger to group sessions), a human-readable `name` shown as
the title, and optionally a `profile` name that groups sessions under a separate entry.

```java
// Create a session. "key" groups sessions from the same day; "name" is the title.
LogSession session = Logger.newSession(context, appName, deviceAddress, deviceName);

// Append entries. A plain message, or a string resource with format arguments:
Logger.log(session, LogContract.Log.Level.INFO, "Log session has been created");
Logger.i(session, "Connected");
Logger.e(session, R.string.error_message, error);
```

Six log levels are available, ordered by priority:
`DEBUG`, `VERBOSE`, `INFO`, `APPLICATION`, `WARNING` and `ERROR`
(see [LogContract.Log.Level]). The `APPLICATION` level was added in version 2.0.

If nRF Logger is not installed and no local provider is configured, these calls do nothing.

### Marks and descriptions

A session may be annotated with a comment and one of six marks (stars/flags):

```java
Logger.setSessionDescription(session, "This is a comment");
Logger.setSessionMark(session, Logger.MARK_FLAG_RED);
```

### Reading entries back

Entries are exposed through a `ContentProvider` and can be queried with a `Cursor`:

```java
final Cursor c = getContentResolver().query(
        session.getSessionEntriesUri(),
        new String[] {
                LogContract.Log.TIME,
                LogContract.Log.LEVEL,
                LogContract.Log.DATA },
        null, null, LogContract.Log.TIME + " ASC");
```

### Working without nRF Logger

Since version 2.0 an app can persist logs even when nRF Logger is not installed by extending
[LocalLogContentProvider] and declaring it in the manifest. The local database is a limited
version of the one in nRF Logger — a single application, no marks and no descriptions:

```xml
<provider
    android:name="com.example.log.provider.MyLogContentProvider"
    android:authorities="com.example.log"
    android:exported="true" />
```

Both remote and local sessions implement the common [ILogSession] interface, so application code
can hold an `ILogSession` reference regardless of which backend is available.

For Timber integration, see the `log-timber` module.

# Package no.nordicsemi.android.log

The public API of the library.

The entry point is [Logger], a helper with static methods for creating sessions
([Logger.newSession]) and appending entries (`Logger.d`, `Logger.v`, `Logger.i`, `Logger.a`,
`Logger.w`, `Logger.e` and the generic `Logger.log`), as well as for setting a session description
or mark.

A session is represented by [ILogSession], implemented by:

- [LogSession] — backed by the nRF Logger application, with full feature support.
- [LocalLogSession] — backed by the app's own [LocalLogContentProvider][no.nordicsemi.android.log.localprovider.LocalLogContentProvider],
  used as a fallback when nRF Logger is not installed.

[LogContract] defines the column names, content `Uri`s, log levels and session parameters used by
the underlying content provider. Use [Logger] for writing rather than accessing the contract
directly.

# Package no.nordicsemi.android.log.annotation

Type-safe `@IntDef` annotations for the integer constants used by the API.

- [LogLevel] restricts a value to one of the [LogContract.Log.Level][no.nordicsemi.android.log.LogContract.Log.Level]
  constants (`DEBUG`, `VERBOSE`, `INFO`, `APPLICATION`, `WARNING`, `ERROR`).
- [LogMark] restricts a value to one of the `Logger.MARK_*` constants used by
  [Logger.setSessionMark][no.nordicsemi.android.log.Logger.setSessionMark].

These annotations are retained at source level only and exist to give compile-time checking and
better IDE hints; they carry no runtime cost.

# Package no.nordicsemi.android.log.localprovider

A local `ContentProvider` implementation used when the nRF Logger application is not installed.

Extend [LocalLogContentProvider] in your application, declare it in the *AndroidManifest.xml* with
a matching authority, and logs written through a [LocalLogSession][no.nordicsemi.android.log.LocalLogSession]
will be stored in a small local SQLite database instead of in nRF Logger. This provider is a
limited version of the one in nRF Logger: it serves a single application and does not support
marking sessions or adding descriptions. nRF Connect uses this provider as a fallback when
nRF Logger is not installed.

The remaining classes in this package (the database helper, transaction and projection map) are
internal implementation details and are not meant to be used directly.
