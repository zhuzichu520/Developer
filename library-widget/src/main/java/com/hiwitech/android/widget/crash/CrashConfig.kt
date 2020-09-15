package com.hiwitech.android.widget.crash

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.NonNull
import java.io.Serializable
import java.lang.reflect.Modifier


class CrashConfig internal constructor() : Serializable {


    @IntDef(BACKGROUND_MODE_CRASH, BACKGROUND_MODE_SHOW_CUSTOM, BACKGROUND_MODE_SILENT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BackgroundMode

    companion object {
        internal const val BACKGROUND_MODE_SILENT = 0
        internal const val BACKGROUND_MODE_SHOW_CUSTOM = 1
        internal const val BACKGROUND_MODE_CRASH = 2
    }

    internal var backgroundMode = BACKGROUND_MODE_SHOW_CUSTOM
    internal var enabled = true
    internal var showErrorDetails = true
    internal var showRestartButton = true
    internal var logErrorOnRestart = true
    internal var trackActivities = false
    internal var minTimeBetweenCrashesMs = 3000
    internal var errorDrawable: Int? = null
    internal var errorActivityClass: Class<out Activity?>? = null
    internal var restartActivityClass: Class<out Activity?>? = null
    internal var eventListener: CrashManager.EventListener? = null


    class Builder {

        private lateinit var config: CrashConfig

        companion object {
            fun create():Builder {
                val builder = Builder()
                val currentConfig = CrashManager.config
                val config = CrashConfig()
                config.backgroundMode = currentConfig.backgroundMode
                config.enabled = currentConfig.enabled
                config.showErrorDetails = currentConfig.showErrorDetails
                config.showRestartButton = currentConfig.showRestartButton
                config.logErrorOnRestart = currentConfig.logErrorOnRestart
                config.trackActivities = currentConfig.trackActivities
                config.minTimeBetweenCrashesMs = currentConfig.minTimeBetweenCrashesMs
                config.errorDrawable = currentConfig.errorDrawable
                config.errorActivityClass = currentConfig.errorActivityClass
                config.restartActivityClass = currentConfig.restartActivityClass
                config.eventListener = currentConfig.eventListener
                builder.config = config
                return builder
            }
        }

        @NonNull
        fun backgroundMode(@BackgroundMode backgroundMode: Int): Builder {
            config.backgroundMode = backgroundMode
            return this
        }

        @NonNull
        fun enabled(enabled: Boolean): Builder {
            config.enabled = enabled
            return this
        }

        @NonNull
        fun showErrorDetails(showErrorDetails: Boolean): Builder {
            config.showErrorDetails = showErrorDetails
            return this
        }

        @NonNull
        fun showRestartButton(showRestartButton: Boolean): Builder {
            config.showRestartButton = showRestartButton
            return this
        }

        @NonNull
        fun logErrorOnRestart(logErrorOnRestart: Boolean): Builder {
            config.logErrorOnRestart = logErrorOnRestart
            return this
        }

        @NonNull
        fun trackActivities(trackActivities: Boolean): Builder? {
            config.trackActivities = trackActivities
            return this
        }

        @NonNull
        fun minTimeBetweenCrashesMs(minTimeBetweenCrashesMs: Int): Builder? {
            config.minTimeBetweenCrashesMs = minTimeBetweenCrashesMs
            return this
        }

        @NonNull
        fun errorDrawable(@DrawableRes errorDrawable: Int?): Builder? {
            config.errorDrawable = errorDrawable
            return this
        }

        @NonNull
        fun errorActivity(errorActivityClass: Class<out Activity>?): Builder {
            config.errorActivityClass = errorActivityClass
            return this
        }


        @NonNull
        fun restartActivity(restartActivityClass: Class<out Activity>?): Builder {
            config.restartActivityClass = restartActivityClass
            return this
        }

        fun eventListener(eventListener: CrashManager.EventListener?): Builder {
            if (
                eventListener != null &&
                eventListener.javaClass.enclosingClass != null &&
                !Modifier.isStatic(eventListener.javaClass.modifiers)
            ) {
                throw IllegalArgumentException("The event listener cannot be an inner or anonymous class, because it will need to be serialized. Change it to a class of its own, or make it a static inner class.");
            } else {
                config.eventListener = eventListener
            }
            return this
        }

        @NonNull
        fun get(): CrashConfig? {
            return config
        }

        fun apply() {
            CrashManager.config = config
        }

    }
}