package com.chuzi.android.shared.http.entity

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("data")
    var `data`: T? = null,
    @SerializedName("errorCode")
    var errorCode: Int? = null,
    @SerializedName("errorMsg")
    var errorMsg: String? = null
)