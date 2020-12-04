package com.chuzi.android.shared.storage

import androidx.appcompat.app.AppCompatDelegate
import com.tencent.mmkv.MMKV

object AppStorage {

    private const val PREFS_NAME = "app"

    private val prefs: Lazy<MMKV> = lazy {
        MMKV.mmkvWithID(PREFS_NAME)
    }

    var token by StringPreference(prefs, null)

    var uiMode by IntPreference(prefs, defaultValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

}