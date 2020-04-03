package com.hiwitech.android.developer.rxhttp.parser

import com.hiwitech.android.shared.ext.new
import com.hiwitech.android.shared.http.entity.Response
import com.hiwitech.android.shared.http.entity.ResponsePageList
import com.hiwitech.android.shared.http.exception.ExceptionManager
import com.hiwitech.android.shared.http.exception.ResponseThrowable
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type


@Parser(name = "ResponsePageList")
class ResponsePageListParser<T>(type: Type?) : AbstractParser<ResponsePageList<T>>(type) {
    override fun onParse(response: okhttp3.Response): ResponsePageList<T>? {
        val type: Type = ParameterizedTypeImpl.get(
            Response::class.java,
            ResponsePageList::class.java,
            mType
        ) //获取泛型类型
        val data: Response<ResponsePageList<T>> = convert(response, type)
        val t: ResponsePageList<T>? = data.data?: new()
        if (data.errorCode != 0) {
            throw ResponseThrowable(
                data.errorCode ?: ExceptionManager.UNKNOWN,
                data.errorMsg ?: "未知错误"
            )
        }
        return t
    }
}