package com.hiwitech.android.shared.storage

import androidx.annotation.WorkerThread
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPreference(
    private val preferences: Lazy<MMKV>,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.value.decodeBool(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.encode(property.name, value)
    }
}

class StringPreference(
    private val preferences: Lazy<MMKV>,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.value.decodeString(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.value.encode(property.name, value)
    }
}

class IntPreference(
    private val preferences: Lazy<MMKV>,
    private val defaultValue: Int
) : ReadWriteProperty<Any, Int> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.value.decodeInt(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.value.encode(property.name, value)
    }
}

class LongPreference(
    private val preferences: Lazy<MMKV>,
    private val defaultValue: Long
) : ReadWriteProperty<Any, Long> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preferences.value.decodeLong(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preferences.value.encode(property.name, value)
    }
}

class FloatPreference(
    private val preferences: Lazy<MMKV>,
    private val defaultValue: Float
) : ReadWriteProperty<Any, Float> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Float {
        return preferences.value.decodeFloat(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        preferences.value.encode(property.name, value)
    }
}
