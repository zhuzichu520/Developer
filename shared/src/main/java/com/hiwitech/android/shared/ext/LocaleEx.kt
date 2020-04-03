package com.hiwitech.android.shared.ext

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import com.hiwitech.android.shared.global.AppGlobal.context
import java.util.*

@Suppress("DEPRECATION")
fun updateApplicationLanguage(newLocale: Locale) {
    val resources = context.resources
    val dm = resources.displayMetrics
    val config = resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Locale.setDefault(newLocale)
        config.setLocale(newLocale)
        val localeList = LocaleList(newLocale)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)
    } else {
        config.setLocale(newLocale)
    }
    resources.updateConfiguration(config, dm)
}

fun Context.localeContextWrapper(newLocale: Locale): ContextWrapper {
    val config = this.resources.configuration
    var context: Context = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Locale.setDefault(newLocale)
            config.setLocale(newLocale)
            val localeList = LocaleList(newLocale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            this.createConfigurationContext(config)
        }
        else -> {
            config.setLocale(newLocale)
            this.createConfigurationContext(config)
        }
    }
    return ContextWrapper(context)
}
