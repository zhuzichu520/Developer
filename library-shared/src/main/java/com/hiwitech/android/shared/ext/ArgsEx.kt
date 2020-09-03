package com.hiwitech.android.shared.ext

import android.app.Activity
import androidx.fragment.app.Fragment
import com.hiwitech.android.libs.tool.toCast
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <U, T> Activity.bindExtra() = BindLoader<U, T>()

fun <U, T> Fragment.bindArgument() = BindLoader<U, T>()

private class IntentDelegate<in U, out T> : ReadOnlyProperty<U, T?> {
    override fun getValue(thisRef: U, property: KProperty<*>): T? {
        return when (thisRef) {
            is Fragment -> thisRef.arguments?.get(property.name)?.toCast()
            is Activity -> thisRef.intent.extras?.get(property.name)?.toCast()
            else -> null
        }
    }

}

class BindLoader<in U, out T> {
    operator fun provideDelegate(thisRef: U, prop: KProperty<*>): ReadOnlyProperty<U, T?> {
        return IntentDelegate()
    }
}