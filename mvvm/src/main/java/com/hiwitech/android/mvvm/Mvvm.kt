package com.hiwitech.android.mvvm

/**
 * desc Mvvm
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
object Mvvm {

    const val KEY_ARG = "arg"
    const val KEY_ARG_JSON = "argJson"

    internal var enterAnim = R.anim.h_enter
    internal var exitAnim = R.anim.h_exit
    internal var popEnterAnim = R.anim.h_pop_enter
    internal var popExitAnim = R.anim.h_pop_exit

    var loadingLayoutId = R.layout.dialog_loading

    fun setAnimBuilder(
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int
    ): Mvvm {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
        this.popEnterAnim = popEnterAnim
        this.popExitAnim = popExitAnim
        return this
    }

}