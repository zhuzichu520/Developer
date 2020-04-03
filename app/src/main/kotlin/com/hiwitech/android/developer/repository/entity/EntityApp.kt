package com.hiwitech.android.developer.repository.entity

import android.graphics.drawable.Drawable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2020-02-09
 * Time: 19:59
 */
data class EntityApp(
    var packageName: String?=null,
    var path: String?=null,
    var fastOpen: Boolean?=null,
    var icon: Drawable?=null,
    var name: CharSequence?=null,
    var version: CharSequence?=null,
    var cloneCount: Int?=null,
    var disableMultiVersion: Boolean?=null
)