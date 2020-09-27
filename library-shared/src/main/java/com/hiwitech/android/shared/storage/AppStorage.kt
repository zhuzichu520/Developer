package com.hiwitech.android.shared.storage

import com.tencent.mmkv.MMKV

object AppStorage {

    private const val PREFS_NAME = "app"

    private val prefs: Lazy<MMKV> = lazy {
        MMKV.mmkvWithID(PREFS_NAME)
    }

    var token by StringPreference(prefs, null)

}