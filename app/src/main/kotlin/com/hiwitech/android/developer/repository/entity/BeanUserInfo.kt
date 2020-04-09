package com.hiwitech.android.developer.repository.entity

import com.google.gson.annotations.SerializedName

data class BeanUserInfo(
    @SerializedName("coinCount")
    var coinCount: Int? = null,
    @SerializedName("level")
    var level: Int? = null,
    @SerializedName("rank")
    var rank: Int? = null,
    @SerializedName("userId")
    var userId: Int? = null,
    @SerializedName("username")
    var username: String? = null
)
