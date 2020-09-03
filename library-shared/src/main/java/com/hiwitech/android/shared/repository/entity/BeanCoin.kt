package com.hiwitech.android.shared.repository.entity

import com.google.gson.annotations.SerializedName

data class BeanCoin(
    @SerializedName("coinCount")
    var coinCount: Int? = null,
    @SerializedName("date")
    var date: Long? = null,
    @SerializedName("desc")
    var desc: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("reason")
    var reason: String? = null,
    @SerializedName("type")
    var type: Int? = null,
    @SerializedName("userId")
    var userId: Int? = null,
    @SerializedName("userName")
    var userName: String? = null
)
