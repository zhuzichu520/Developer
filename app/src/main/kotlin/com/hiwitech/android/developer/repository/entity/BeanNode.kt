package com.hiwitech.android.developer.repository.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
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
) : Parcelable