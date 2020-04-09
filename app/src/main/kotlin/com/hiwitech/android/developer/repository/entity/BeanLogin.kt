package com.hiwitech.android.developer.repository.entity

import com.google.gson.annotations.SerializedName

data class BeanLogin(
    @SerializedName("admin")
    var admin: Boolean? = null,
    @SerializedName("chapterTops")
    var chapterTops: List<Any?>? = null,
    @SerializedName("collectIds")
    var collectIds: List<Any?>? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("publicName")
    var publicName: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("type")
    var type: Int? = null,
    @SerializedName("username")
    var username: String? = null
)
