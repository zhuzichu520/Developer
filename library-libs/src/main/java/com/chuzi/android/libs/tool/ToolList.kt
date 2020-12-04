@file: JvmName("ToolList")

package com.chuzi.android.libs.tool

/**
 * 集合是否为空（null 或 size==0）。
 *
 * @param sourceList 源集合数据
 * @return true：为空
 */
fun <V> isNullOrEmpty(sourceList: List<V>?): Boolean = sourceList.isNullOrEmpty()

/**
 * 集合中元素个数是否大于0。
 *
 * @param sourceList 源集合数据
 * @return true：是
 */
fun <V> isNotEmpty(sourceList: List<V>?): Boolean = !isNullOrEmpty(sourceList)

fun <V> doNotEmpty(sourceList: List<V>?, closure: List<V>.() -> Unit) {
    if (!sourceList.isNullOrEmpty())
        closure.invoke(sourceList)
}

/**
 * 获取集合中元素个数。
 *
 * @param sourceList 源集合数据
 * @return 集合元素个数
 */
fun <V> getSize(sourceList: List<V>?) = sourceList?.size ?: 0

/**
 * 将源集合中的元素 复制 到目标集合中。
 *
 * @param sourceList 源集合数据
 * @param destList   目标集合
 */
fun <V> copy(sourceList: List<V>?, destList: MutableList<V>?) {
    if (isNotEmpty(sourceList) && (destList != null)) {
        destList.addAll(sourceList!!)
    }
}

/**
 * 清除集合中的元素。
 * 注意：通过 [java.util.Arrays.asList] 创建的List不能调用该函数，否则会抛出异常。
 *
 * @param sourceList 源集合数据
 */
fun <V> clear(sourceList: MutableList<V>?) {
    if (isNotEmpty(sourceList)) {
        sourceList?.clear()
    }

}

/**
 * 替换List某个元素。
 *
 * @param index 要替换的元素下标
 */
fun <T> List<T>.replaceAt(index: Int, replace: (T) -> T): List<T> = ArrayList(this).apply {
    val item = this[index]
    this[index] = replace(item)
}