package com.chuzi.android.shared.http.exception

class ResponseThrowable(var code: Int, override var message: String) : RuntimeException()
