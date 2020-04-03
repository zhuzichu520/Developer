package com.hiwitech.android.shared.log.lumberjack.util

class LabeledValueBuilder(val separator: String = "|", val equalSign: String = "=") {

    var count: Int = 0
    var data: String = ""

    fun addPair(label: String, item: Any): LabeledValueBuilder {
        data += "${if (count > 0) " $separator " else ""}$label $equalSign $item"
        count++
        return this
    }

    fun build(): String = data
}