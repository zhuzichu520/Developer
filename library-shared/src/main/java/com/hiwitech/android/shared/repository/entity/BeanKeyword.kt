package com.hiwitech.android.shared.repository.entity
import com.google.gson.annotations.SerializedName

data class BeanKeyword(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("link")
    var link: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("order")
    var order: Int? = null,
    @SerializedName("visible")
    var visible: Int? = null
)
