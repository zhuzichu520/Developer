package com.hiwitech.android.developer.base

import android.os.Bundle
import com.hiwitech.android.mvvm.base.BaseActivity
import com.hiwitech.android.shared.theme.ThemeManager

abstract class ActivityBase : BaseActivity() {

    override fun onCreateNow(savedInstanceState: Bundle?) {
        ThemeManager.applyThemeOverlays(this)
        super.onCreateNow(savedInstanceState)
    }

}
