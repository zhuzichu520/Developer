package com.chuzi.android.shared.base

import android.os.Bundle
import com.chuzi.android.mvvm.base.BaseActivity
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

abstract class ActivityBase : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val skinManager = QMUISkinManager.defaultInstance(this)
        setSkinManager(skinManager)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
    }

}
