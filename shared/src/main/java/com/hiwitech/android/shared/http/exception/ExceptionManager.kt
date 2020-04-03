package com.hiwitech.android.shared.http.exception

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException

@Suppress("unused", "MemberVisibilityCanBePrivate")
object ExceptionManager {

    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408
    const val INTERNAL_SERVER_ERROR = 500
    const val SERVICE_UNAVAILABLE = 503

    const val UNKNOWN = 1000
    const val PARSE_ERROR = 1001
    const val NETWORD_ERROR = 1002
    const val SSL_ERROR = 1005
    const val TIMEOUT_ERROR = 1006


    fun handleException(throwable: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        if (throwable is HttpException) {
            ex = ResponseThrowable(UNKNOWN, "未知错误")
            when (throwable.code()) {
                UNAUTHORIZED -> ex.message = "操作未授权"
                FORBIDDEN -> ex.message = "请求被拒绝"
                NOT_FOUND -> ex.message = "资源不存在"
                REQUEST_TIMEOUT -> ex.message = "服务器执行超时"
                INTERNAL_SERVER_ERROR -> ex.message = "服务器内部错误"
                SERVICE_UNAVAILABLE -> ex.message = "服务器不可用"
                else -> ex.message = "网络错误"
            }
            ex.code = throwable.code()
            return ex
        } else if (throwable is JsonParseException
            || throwable is JSONException
            || throwable is ParseException || throwable is MalformedJsonException
        ) {
            ex = ResponseThrowable(PARSE_ERROR, "解析错误")
            return ex
        } else if (throwable is ConnectException) {
            ex = ResponseThrowable(NETWORD_ERROR, "连接失败")
            return ex
        } else if (throwable is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(SSL_ERROR, "证书验证失败")
            return ex
        } else if (throwable is java.net.SocketTimeoutException) {
            ex = ResponseThrowable(TIMEOUT_ERROR, "连接超时")
            return ex
        } else if (throwable is java.net.UnknownHostException) {
            ex = ResponseThrowable(TIMEOUT_ERROR, "主机地址未知")
            return ex
        } else if (throwable is ResponseThrowable) {
            ex = throwable
            return ex
        } else {
            ex = ResponseThrowable(UNKNOWN, "未知错误")
            return ex
        }
    }

}