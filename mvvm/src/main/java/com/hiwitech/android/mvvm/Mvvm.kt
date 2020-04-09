package com.hiwitech.android.mvvm

import androidx.navigation.AnimBuilder
import androidx.navigation.NavOptions

/**
 * desc Mvvm
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
object Mvvm {

    const val KEY_ARG = "arg";
    const val KEY_ARG_JSON = "argJson"

    internal var enterAnim = R.anim.h_enter
    internal var exitAnim = R.anim.h_exit
    internal var popEnterAnim = R.anim.h_pop_enter
    internal var popExitAnim = R.anim.h_pop_exit

    fun setAnimBuilder(animBuilder: AnimBuilder): Mvvm {
        enterAnim = animBuilder.enter
        exitAnim = animBuilder.exit
        popEnterAnim = animBuilder.popEnter
        popExitAnim = animBuilder.popExit
        return this
    }

    internal fun getDefaultNavOptions(
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?,
        animBuilder: AnimBuilder?
    ): NavOptions {
        return NavOptions.Builder().apply {
            if (popUpTo != null && inclusive != null) {
                setPopUpTo(popUpTo, inclusive)
            }
            singleTop?.let {
                setLaunchSingleTop(singleTop)
            }
            setEnterAnim(animBuilder?.enter ?: enterAnim)
            setExitAnim(animBuilder?.exit ?: exitAnim)
            setPopEnterAnim(animBuilder?.popEnter ?: popEnterAnim)
            setPopExitAnim(animBuilder?.popExit ?: popExitAnim)
        }.build()
    }

}