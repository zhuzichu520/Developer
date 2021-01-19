package com.chuzi.android.shared.theme

import androidx.appcompat.app.AppCompatDelegate
import com.tencent.mmkv.MMKV
import com.chuzi.android.shared.storage.IntPreference

object ThemeStorage {
    private const val PREFS_NAME = "mmkv_theme"

    private val prefs: Lazy<MMKV> = lazy {
        MMKV.mmkvWithID(PREFS_NAME)!!
    }

    var uiMode by IntPreference(
        prefs,
        defaultValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    )

    var primarColorIndex by IntPreference(
        prefs,
        defaultValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    )
    var secondaryColorIndex by IntPreference(
        prefs,
        -1
    )
    var cornerFamilyIndex by IntPreference(
        prefs,
        -1
    )
    var cornerSizeIndex by IntPreference(
        prefs,
        -1
    )
}