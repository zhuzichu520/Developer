package com.chuzi.android.shared.http.exception

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
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
        if (throwable is JsonParseException
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