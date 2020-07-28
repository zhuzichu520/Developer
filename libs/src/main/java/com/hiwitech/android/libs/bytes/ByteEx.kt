package com.hiwitech.android.libs.bytes

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/28 10:42 AM
 * since: v 1.0.0
 */
/**
 * 字节转int，最多4个字节
 * @param bytes
 * @return
 */
fun byteArrayToInt(bytes: ByteArray): Int {
    if (bytes.size > 4) {
        return 0
    }
    val intBytes = ByteArray(4)
    val length = bytes.size
    System.arraycopy(bytes, 0, intBytes, 4 - length, length)
    return intBytes[3].toInt() and 0xFF or (
            intBytes[2].toInt() and 0xFF shl 8) or (
            intBytes[1].toInt() and 0xFF shl 16) or (
            intBytes[0].toInt() and 0xFF shl 24)
}


/**
 * int转字节
 * @param number int值
 * @param length 字节位数
 * @return
 */
fun intToByteArray(number: Int, length: Int): ByteArray? {
    when (length) {
        1 -> {
            return byteArrayOf(number.toByte())
        }
        2 -> {
            return byteArrayOf(
                (number shr 8 and 0xFF).toByte(),
                (number and 0xFF).toByte()
            )
        }
        3 -> {
            return byteArrayOf(
                (number shr 16 and 0xFF).toByte(),
                (number shr 8 and 0xFF).toByte(),
                (number and 0xFF).toByte()
            )
        }
        else -> return byteArrayOf(
            (number shr 24 and 0xFF).toByte(),
            (number shr 16 and 0xFF).toByte(),
            (number shr 8 and 0xFF).toByte(),
            (number and 0xFF).toByte()
        )
    }
}
