package com.chuzi.android.shared.repository.entity

import com.google.gson.annotations.SerializedName

data class BeanNode(
    @SerializedName("children")
    var children: List<BeanNode>? = null,
    @SerializedName("courseId")
    var courseId: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("order")
    var order: Int? = null,
    @SerializedName("parentChapterId")
    var parentChapterId: Int? = null,
    @SerializedName("userControlSetTop")
    var userControlSetTop: Boolean? = null,
    @SerializedName("visible")
    var visible: Int? = null
)
