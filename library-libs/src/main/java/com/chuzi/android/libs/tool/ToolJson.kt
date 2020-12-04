@file: JvmName("ToolJson")

package com.chuzi.android.libs.tool

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private val GSON by lazy {
    val gsonBuilder: GsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
    gsonBuilder.disableHtmlEscaping() //禁止将部分特殊字符转义为unicode编码
    gsonBuilder.create()
}


/**
 * list 转 json
 *
 * @param list 数据源（List集合）
 * @return json字符串
 */
fun list2Json(list: List<*>?): String? {
    return GSON.toJson(list)
}

/**
 * map 转 json
 *
 * @param map 数据源（Map集合）
 * @return json字符串
 */
fun map2Json(map: Map<*, *>?): String? {
    return GSON.toJson(map)
}

/**
 * 数组 转 json
 *
 * @param array 数据源（数组）
 * @return json字符串
 */
fun array2Json(array: Array<*>?): String? {
    return GSON.toJson(array)
}

/**
 * 任意对象 转 json
 *
 * @param any 数据源（任意对象）
 * @return json字符串
 */
fun object2Json(any: Any?): String? {
    return GSON.toJson(any)
}

/**
 * json 转 List集合
 *
 * @param json  json字符串
 * @param T     集合中元素的范型
 * @return List集合
 */
fun <T> json2List(json: String?, type: Class<T>): List<T>? {
    return GSON.fromJson(json, TypeToken.getParameterized(ArrayList::class.java, type).type)
}

/**
 * gson 转 List集合
 *
 * @param json  json字符串
 * @param T     集合中元素的范型
 * @return List集合
 */
fun <T> gson2List(json: String?): List<T>? {
    return GSON.fromJson(json, object : TypeToken<List<T>>() {}.type)
}

/**
 * json 转 Map集合
 *
 * @param json  json字符串
 * @return Map集合
 */
fun <K, V> json2Map(json: String?, typeK: Class<K>, typeV: Class<V>): Map<K, V>? {
    return GSON.fromJson(json, TypeToken.getParameterized(HashMap::class.java, typeK, typeV).type)
}

/**
 * json 转 Map集合
 *
 * @param json  json字符串
 * @return Map集合
 */
fun <K, V> json2Maps(json: String?, typeK: Class<K>, typeV: Class<V>): List<Map<K, V>>? {
    return GSON.fromJson(
        json,
        TypeToken.getParameterized(
            ArrayList::class.java,
            TypeToken.getParameterized(HashMap::class.java, typeK, typeV).type
        ).type
    )
}

/**
 * json 转 Map集合 List
 *
 * @param json  json字符串
 * @return Map集合
 */
fun <K, V> json2MapList(json: String?, typeK: Class<K>, typeV: Class<V>): Map<K, List<V>>? {
    return GSON.fromJson(
        json,
        TypeToken.getParameterized(
            HashMap::class.java,
            typeK,
            TypeToken.getParameterized(ArrayList::class.java, typeV).type
        ).type
    )
}

/**
 * json 转 Map集合 List
 *
 * @param json  json字符串
 * @return Map集合
 */
fun <K, V> json2MapArrayList(
    json: String?,
    typeK: Class<K>,
    typeV: Class<V>
): Map<K, ArrayList<V>>? {
    return GSON.fromJson(
        json,
        TypeToken.getParameterized(
            HashMap::class.java,
            typeK,
            TypeToken.getParameterized(ArrayList::class.java, typeV).type
        ).type
    )
}

/**
 * json 转 EnumMap集合 ArrayList
 *
 * @param json  json字符串
 * @return Map集合
 */
fun <K, V> json2EnumMapArrayList(
    json: String?,
    typeK: Class<K>,
    typeV: Class<V>
): Map<K, ArrayList<V>>? {
    return GSON.fromJson(
        json,
        TypeToken.getParameterized(
            EnumMap::class.java,
            typeK,
            TypeToken.getParameterized(ArrayList::class.java, typeV).type
        ).type
    )
}


/**
 * json 转 任意对象
 *
 * @param json  json字符串
 * @param clazz 集合中元素的Class
 * @param T     集合中元素的范型
 * @return 范型对应的实例
 */
fun <T> json2Object(json: String?, clazz: Class<T>): T? {
    return GSON.fromJson(json, clazz)
}

/**
 * json 转 任意对象
 *
 * @param json  json字符串
 * @param type 集合中元素的type
 * @param T     集合中元素的范型
 * @return 范型对应的实例
 */
fun <T> json2Object(json: String?, type: Type): T? {
    return GSON.fromJson(json, type)
}