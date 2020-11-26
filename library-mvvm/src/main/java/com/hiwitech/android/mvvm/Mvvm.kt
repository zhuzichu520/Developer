package com.hiwitech.android.mvvm

import com.qmuiteam.qmui.arch.QMUIFragment

/**
 * desc Mvvm
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
object Mvvm {

    const val KEY_ARG = "arg"
    const val KEY_ARG_JSON = "argJson"

    internal var transitionConfig: QMUIFragment.TransitionConfig =
        QMUIFragment.SLIDE_TRANSITION_CONFIG


    var loadingLayoutId = R.layout.widget_layout_loading

    fun setAnimBuilder(
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int
    ): Mvvm {
        transitionConfig = QMUIFragment.TransitionConfig(
            enterAnim,
            exitAnim,
            popEnterAnim,
            popExitAnim
        )
        return this
    }

}