package com.hiwitech.android.widget.crash

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RestrictTo
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.widget.log.lumberjack.L
import java.io.*
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.system.exitProcess


object CrashManager {

    private const val TAG = "CrashManager"

    private const val EXTRA_CONFIG = "com.hiwitech.android.widget.crash.EXTRA_CONFIG"
    private const val EXTRA_STACK_TRACE = "com.hiwitech.android.widget.crash.EXTRA_STACK_TRACE"
    private const val EXTRA_ACTIVITY_LOG = "com.hiwitech.android.widget.crash.EXTRA_ACTIVITY_LOG"

    private const val INTENT_ACTION_ERROR_ACTIVITY = "com.hiwitech.android.widget.crash.ERROR"
    private const val INTENT_ACTION_RESTART_ACTIVITY = "com.hiwitech.android.widget.crash.RESTART"
    private const val CAOC_HANDLER_PACKAGE_NAME = "com.hiwitech.android.widget.crash"
    private const val DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os"
    private const val TIME_TO_CONSIDER_FOREGROUND_MS = 500
    private const val MAX_STACK_TRACE_SIZE = 131071 //128 KB - 1

    private const val MAX_ACTIVITIES_IN_LOG = 50

    private const val SHARED_PREFERENCES_FILE = "custom_activity_on_crash"
    private const val SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp"

    @SuppressLint("StaticFieldLeak")
    private lateinit var application: Application
    var config: CrashConfig = CrashConfig()
    private val activityLog: Deque<String> = ArrayDeque(MAX_ACTIVITIES_IN_LOG)
    private var lastActivityCreated: WeakReference<Activity?> = WeakReference(null)
    private var lastActivityCreatedTimestamp = 0L
    private var isInBackground = true


    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun install(context: Context?) {
        try {
            context?.let {
                val oldHandler = Thread.getDefaultUncaughtExceptionHandler()
                if (
                    oldHandler != null &&
                    oldHandler.javaClass.name.startsWith(CAOC_HANDLER_PACKAGE_NAME)
                ) {
                    L.tag(TAG).i { "CrashManager was already installed, doing nothing!" }
                } else {
                    if (oldHandler != null &&
                        !oldHandler.javaClass.name.startsWith(DEFAULT_HANDLER_PACKAGE_NAME)
                    ) {
                        L.tag(TAG).i { "CrashManager was already installed, doing nothing!" }
                    }
                    application = context.applicationContext as Application
                    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                        if (config.enabled) {
                            L.tag(TAG)
                                .e(throwable) { "App has crashed, executing CustomActivityOnCrash's UncaughtExceptionHandler" }
                            if (hasCrashedInTheLastSeconds(application)) {
                                L.tag(TAG)
                                    .e(throwable) { "App already crashed recently, not starting custom error activity because we could enter a restart loop. Are you sure that your app does not crash directly on init?" }
                                if (oldHandler != null) {
                                    oldHandler.uncaughtException(thread, throwable)
                                    return@setDefaultUncaughtExceptionHandler
                                }
                            } else {
                                setLastCrashTimestamp(application, Date().time)
                                var errorActivityClass: Class<out Activity>? =
                                    config.errorActivityClass
                                if (errorActivityClass == null) {
                                    errorActivityClass = guessErrorActivityClass(application)
                                }

                                if (isStackTraceLikelyConflictive(throwable)) {
                                    L.tag(TAG)
                                        .i { "Your application class or your error activity have crashed, the custom activity will not be launched!" }
                                    if (oldHandler != null) {
                                        oldHandler.uncaughtException(thread, throwable)
                                        return@setDefaultUncaughtExceptionHandler
                                    }
                                } else if (config.backgroundMode == CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM || !isInBackground || lastActivityCreatedTimestamp >= Date().time - TIME_TO_CONSIDER_FOREGROUND_MS) {
                                    val intent = Intent(application, errorActivityClass)
                                    val sw = StringWriter()
                                    val pw = PrintWriter(sw)
                                    throwable.printStackTrace(pw)
                                    var stackTraceString: String = sw.toString()

                                    if (stackTraceString.length > MAX_STACK_TRACE_SIZE) {
                                        val disclaimer = " [stack trace too large]"
                                        stackTraceString = stackTraceString.substring(
                                            0,
                                            MAX_STACK_TRACE_SIZE - disclaimer.length
                                        ) + disclaimer
                                    }
                                    intent.putExtra(EXTRA_STACK_TRACE, stackTraceString)
                                    if (config.trackActivities) {
                                        val activityLogStringBuilder = StringBuilder()
                                        while (!activityLog.isEmpty()) {
                                            activityLogStringBuilder.append(activityLog.poll())
                                        }
                                        intent.putExtra(
                                            EXTRA_ACTIVITY_LOG,
                                            activityLogStringBuilder.toString()
                                        )
                                    }

                                    if (config.showRestartButton && config.restartActivityClass == null) { //We can set the restartActivityClass because the app will terminate right now,
                                        config.restartActivityClass =
                                            guessRestartActivityClass(application)
                                    }

                                    intent.putExtra(EXTRA_CONFIG, config)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    config.eventListener?.onLaunchErrorActivity()
                                    application.startActivity(intent)
                                } else if (config.backgroundMode == CrashConfig.BACKGROUND_MODE_CRASH) {
                                    if (oldHandler != null) {
                                        oldHandler.uncaughtException(thread, throwable)
                                        return@setDefaultUncaughtExceptionHandler
                                    }
                                }
                            }

                            val lastActivity = lastActivityCreated.get()
                            if (lastActivity != null) {
                                lastActivity.finish()
                                lastActivityCreated.clear()
                            }
                            killCurrentProcess()
                        } else oldHandler?.uncaughtException(thread, throwable)
                    }

                    application.registerActivityLifecycleCallbacks(object :
                        Application.ActivityLifecycleCallbacks {

                        var currentlyStartedActivities = 0
                        val dateFormat: DateFormat =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

                        override fun onActivityPaused(activity: Activity) {
                            if (config.trackActivities) {
                                activityLog.add(
                                    dateFormat.format(Date())
                                        .toString() + ": " + activity.javaClass.simpleName + " paused\n"
                                )
                            }
                        }

                        override fun onActivityStarted(activity: Activity) {
                            currentlyStartedActivities++
                            isInBackground = (currentlyStartedActivities == 0)
                        }

                        override fun onActivityDestroyed(activity: Activity) {
                            if (config.trackActivities) {
                                activityLog.add(
                                    dateFormat.format(Date())
                                        .toString() + ": " + activity.javaClass.simpleName + " destroyed\n"
                                )
                            }
                        }

                        override fun onActivitySaveInstanceState(
                            activity: Activity,
                            bundle: Bundle
                        ) {
                        }

                        override fun onActivityStopped(activity: Activity) {
                            currentlyStartedActivities--
                            isInBackground = (currentlyStartedActivities == 0)
                        }

                        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                            if (activity.javaClass != config.errorActivityClass) {
                                lastActivityCreated = WeakReference(activity)
                                lastActivityCreatedTimestamp = Date().time
                            }
                            if (config.trackActivities) {
                                activityLog.add(
                                    dateFormat.format(Date())
                                        .toString() + ": " + activity.javaClass.simpleName + " created\n"
                                )
                            }
                        }

                        override fun onActivityResumed(activity: Activity) {
                            if (config.trackActivities) {
                                activityLog.add(
                                    dateFormat.format(Date())
                                        .toString() + ": " + activity.javaClass.simpleName + " resumed\n"
                                )
                            }
                        }
                    })
                }
                L.tag(TAG).i { "CrashManager has been installed." }
            } ?: let {
                L.tag(TAG).i { "Install failed: context is null!" }
            }
        } catch (t: Throwable) {
            L.tag(TAG).e(t) { "CrashManager.install  Error" }
        }
    }


    @Nullable
    fun getStackTraceFromIntent(@NonNull intent: Intent): String? {
        return intent.getStringExtra(EXTRA_STACK_TRACE)
    }


    @Nullable
    fun getConfigFromIntent(@NonNull intent: Intent): CrashConfig? {
        val config: CrashConfig =
            intent.getSerializableExtra(EXTRA_CONFIG) as CrashConfig
        if (config.logErrorOnRestart) {
            val stackTrace = getStackTraceFromIntent(intent)
            if (stackTrace != null) {
                L.tag(TAG).i {
                    "The previous app process crashed. This is the stack trace of the crash:\n" + getStackTraceFromIntent(
                        intent
                    )
                }
            }
        }
        return config
    }


    @Nullable
    fun getActivityLogFromIntent(@NonNull intent: Intent): String? {
        return intent.getStringExtra(EXTRA_ACTIVITY_LOG)
    }

    @NonNull
    fun getAllErrorDetailsFromIntent(
        @NonNull context: Context,
        @NonNull intent: Intent
    ): String? { //I don't think that this needs localization because it's a development string...
        val currentDate = Date()
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val buildDateAsString = getBuildDateAsString(context, dateFormat)
        val versionName = getVersionName(context)
        var errorDetails = ""
        errorDetails += "Build version: $versionName \n"
        if (buildDateAsString != null) {
            errorDetails += "Build date: $buildDateAsString \n"
        }
        errorDetails += "Current date: " + dateFormat.format(currentDate) + " \n"
        errorDetails += "Device: " + getDeviceModelName() + " \n"
        errorDetails += "OS version: Android " + Build.VERSION.RELEASE.toString() + " (SDK " + Build.VERSION.SDK_INT.toString() + ") \n \n"
        errorDetails += "Stack trace:  \n"
        errorDetails += getStackTraceFromIntent(intent)
        val activityLog = getActivityLogFromIntent(intent)
        if (activityLog != null) {
            errorDetails += "\nUser actions: \n"
            errorDetails += activityLog
        }
        return errorDetails
    }

    private fun restartApplicationWithIntent(
        @NonNull activity: Activity,
        @NonNull intent: Intent,
        @NonNull config: CrashConfig
    ) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        if (intent.component != null) {
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
        }
        config.eventListener?.onRestartAppFromErrorActivity()
        activity.finish()
        activity.startActivity(intent)
        killCurrentProcess()
    }

    fun restartApplication(@NonNull activity: Activity, @NonNull config: CrashConfig) {
        val intent = Intent(activity, config.restartActivityClass)
        restartApplicationWithIntent(activity, intent, config)
    }

    fun closeApplication(@NonNull activity: Activity, @NonNull config: CrashConfig) {
        config.eventListener?.onCloseAppFromErrorActivity()
        activity.finish()
        killCurrentProcess()
    }


    private fun isStackTraceLikelyConflictive(@NonNull throwable: Throwable): Boolean {
        var process: String?
        try {
            val br = BufferedReader(FileReader("/proc/self/cmdline"))
            process = br.readLine().trim()
            br.close()
        } catch (e: IOException) {
            process = null
        }
        if (process != null && process.endsWith(":error_activity")) {
            return true
        }
        var t: Throwable? = throwable
        do {
            val stackTrace = t!!.stackTrace
            for (element in stackTrace) {
                if (element.className == "android.app.ActivityThread" && element.methodName == "handleBindApplication") {
                    return true
                }
            }
        } while (t!!.cause.also { t = it } != null)
        return false
    }

    @Nullable
    private fun getBuildDateAsString(
        @NonNull context: Context,
        @NonNull dateFormat: DateFormat
    ): String? {
        var buildDate: Long
        try {
            val ai: ApplicationInfo =
                context.packageManager.getApplicationInfo(context.packageName, 0)
            val zf = ZipFile(ai.sourceDir)
            val ze: ZipEntry = zf.getEntry("classes.dex")
            buildDate = ze.time
            zf.close()
        } catch (e: Exception) {
            buildDate = 0
        }
        return if (buildDate > 312764400000L) {
            dateFormat.format(Date(buildDate))
        } else {
            null
        }
    }


    @NonNull
    private fun getVersionName(context: Context): String {
        return try {
            val packageInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "Unknown"
        }
    }

    @NonNull
    private fun getDeviceModelName(): String {
        val manufacturer: String = Build.MANUFACTURER
        val model: String = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    @NonNull
    private fun capitalize(@Nullable s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    @Nullable
    private fun guessRestartActivityClass(@NonNull context: Context): Class<out Activity?>? {
        var resolvedActivityClass: Class<out Activity?>?
        //If action is defined, use that
        resolvedActivityClass = getRestartActivityClassWithIntentFilter(context)
        //Else, get the default launcher activity
        if (resolvedActivityClass == null) {
            resolvedActivityClass = getLauncherActivity(context)
        }
        return resolvedActivityClass
    }

    @Nullable
    private fun getRestartActivityClassWithIntentFilter(@NonNull context: Context): Class<out Activity?>? {
        val searchedIntent =
            Intent().setAction(INTENT_ACTION_RESTART_ACTIVITY)
                .setPackage(context.packageName)
        val resolveInfos: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(
                searchedIntent,
                PackageManager.GET_RESOLVED_FILTER
            )
        if (resolveInfos.isNotEmpty()) {
            val resolveInfo: ResolveInfo = resolveInfos[0]
            try {
                return Class.forName(resolveInfo.activityInfo.name).toCast()
            } catch (e: ClassNotFoundException) {
                L.tag(TAG)
                    .e(e) { "Failed when resolving the restart activity class via intent filter, stack trace follows!" }
            }
        }
        return null
    }

    @Nullable
    private fun getLauncherActivity(@NonNull context: Context): Class<out Activity?>? {
        val intent =
            context.packageManager.getLaunchIntentForPackage(context.packageName)
        if (intent != null && intent.component != null) {
            try {
                return Class.forName(intent.component!!.className).toCast()
            } catch (e: ClassNotFoundException) {
                L.tag(TAG)
                    .e(e) { "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!" }
            }
        }
        return null
    }


    @NonNull
    private fun guessErrorActivityClass(@NonNull context: Context): Class<out Activity?>? {
        var resolvedActivityClass: Class<out Activity?>?
        resolvedActivityClass = getErrorActivityClassWithIntentFilter(context)
        if (resolvedActivityClass == null) {
            resolvedActivityClass = DefaultErrorActivity::class.java
        }
        return resolvedActivityClass
    }


    @Nullable
    private fun getErrorActivityClassWithIntentFilter(@NonNull context: Context): Class<out Activity?>? {
        val searchedIntent =
            Intent().setAction(INTENT_ACTION_ERROR_ACTIVITY)
                .setPackage(context.packageName)
        val resolveInfos: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(
                searchedIntent,
                PackageManager.GET_RESOLVED_FILTER
            )
        if (resolveInfos.isNotEmpty()) {
            val resolveInfo: ResolveInfo = resolveInfos[0]
            try {
                return Class.forName(resolveInfo.activityInfo.name).toCast()
            } catch (e: ClassNotFoundException) { //Should not happen, print it to the log!
                L.tag(TAG).e(e) {
                    "Failed when resolving the error activity class via intent filter, stack trace follows!"
                }
            }
        }
        return null
    }


    private fun killCurrentProcess() {
        Process.killProcess(Process.myPid())
        exitProcess(10)
    }

    @SuppressLint("ApplySharedPref") //This must be done immediately since we are killing the app
    private fun setLastCrashTimestamp(@NonNull context: Context, timestamp: Long) {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
            .edit().putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, timestamp).commit()
    }

    private fun getLastCrashTimestamp(@NonNull context: Context): Long {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_FILE,
            Context.MODE_PRIVATE
        ).getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1)
    }

    private fun hasCrashedInTheLastSeconds(@NonNull context: Context): Boolean {
        val lastTimestamp = getLastCrashTimestamp(context)
        val currentTimestamp = Date().time
        return lastTimestamp <= currentTimestamp && currentTimestamp - lastTimestamp < config.minTimeBetweenCrashesMs
    }


    interface EventListener : Serializable {
        fun onLaunchErrorActivity()

        fun onRestartAppFromErrorActivity()

        fun onCloseAppFromErrorActivity()
    }
}