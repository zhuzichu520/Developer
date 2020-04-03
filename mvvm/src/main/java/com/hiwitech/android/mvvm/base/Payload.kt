package com.hiwitech.android.mvvm.base

import androidx.navigation.AnimBuilder

internal sealed class Payload {

    internal data class Start(
        val actionId: Int,
        val arg: BaseArg,
        val animBuilder: AnimBuilder?,
        val destinationId: Int?,
        val popUpTo: Int?,
        val inclusive: Boolean?,
        val singleTop: Boolean?
    )

}