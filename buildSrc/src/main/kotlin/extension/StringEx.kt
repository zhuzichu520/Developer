package extension

import java.util.*


fun String.quotes(): String {
    return """"$this""""
}

fun String.toInt2(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        throw Exception("请输入合法的数字")
    }
}

fun String?.base64(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    try {
        return Base64.getEncoder().encodeToString(this.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}