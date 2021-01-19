/**
 * desc
 * author: 朱子楚
 * time: 2020/4/8 2:07 PM
 * since: v 1.0.0
 */

object Dcendents {
    const val GROUP = "com.chuzi.android"
    const val VERSION = "3.2.5"
}

/**
 * 依赖版本
 */
object Versions {
    const val APPCOMPAT = "1.3.0-alpha01"
    const val EXIFINTERFACE = "1.3.2"
    const val RECYCLERVIEW = "1.1.0"
    const val CORE_KTX = "1.3.2"
    const val OKHTTP = "4.9.0"
    const val RXJAVA = "3.0.9"
    const val RXANDROID = "3.0.0"
    const val RXBINDING = "4.0.0"
    const val RXHTTP = "2.5.3"
    const val RXLIFE = "3.0.0"
    const val RXPERMISSIONS = "0.12"
    const val GLIDE = "4.11.0"
    const val GLIDE_TRANSFORMATIONS = "4.3.0"
    const val MATERIAL = "1.2.0"
    const val BINDING_COLLECTION_ADAPTER = "4.0.0"
    const val TIMBER = "4.7.1"
    const val LOGBACK = "2.0.0"
    const val SLF4J = "1.7.30"
    const val SWIPEREFRESHLAYOUT = "1.1.0"
    const val FLEXBOX = "2.0.1"
    const val CONSTRAINTLAYOUT = "2.0.4"
    const val MMKV = "1.2.7"
    const val MULTIDEX = "2.0.1"
    const val ONCE = "1.3.0"
    const val AUTOSIZE = "1.2.1"
    const val AGENTWEB = "4.1.4"
    const val GUAVA = "27.0.1-android"
    const val EASYFLOAT = "1.3.4"
    const val GSON = "2.8.6"
    const val AROUTE_API = "1.5.1"
    const val AROUTE_COMPILER = "1.5.1"
    const val QMUI = "2.0.0-alpha11"
    const val DEVELOPER = "3.0.10"
}

/**
 * 插件版本
 */
object BuildPluginsVersion {
    const val AGP = "7.0.0-alpha04"
    const val KOTLIN = "1.4.30-RC"
}

/**
 * 插件库
 */
object ClassPaths {
    const val androidBuildTools = "com.android.tools.build:gradle:${BuildPluginsVersion.AGP}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildPluginsVersion.KOTLIN}"
}

/**
 * Android基础库
 */
object SupportLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val ANDROIDX_EXIFINTERFACE =
        "androidx.exifinterface:exifinterface:${Versions.EXIFINTERFACE}"
    const val ANDROIDX_RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
    const val ANDROIDX_CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT}"
    const val ANDROIDX_SWIPEREFRESHLAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPEREFRESHLAYOUT}"
}

object MyLibs {
    const val DEVELOPER_WIDGET =
        "com.github.zhuzichu520.Developer:library-widget:${Versions.DEVELOPER}"
    const val DEVELOPER_LIBS =
        "com.github.zhuzichu520.Developer:library-libs:${Versions.DEVELOPER}"
    const val DEVELOPER_MVVM =
        "com.github.zhuzichu520.Developer:library-mvvm:${Versions.DEVELOPER}"
}

/**
 * 第三方其他库
 */
object Libs {
    const val GLIDE_OKHTTP = "com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_TRANSFORMATIONS =
        "jp.wasabeef:glide-transformations:${Versions.GLIDE_TRANSFORMATIONS}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val RXHTTP = "com.ljx.rxhttp:rxhttp:${Versions.RXHTTP}"
    const val RXLIFE = "com.ljx.rxlife3:rxlife-rxjava:${Versions.RXLIFE}"
    const val RXJAVA = "io.reactivex.rxjava3:rxjava:${Versions.RXJAVA}"
    const val RXANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RXANDROID}"
    const val RXBINDING_CORE = "com.jakewharton.rxbinding4:rxbinding-core:${Versions.RXBINDING}"
    const val RXPERMISSIONS = "com.github.tbruyelle:rxpermissions:${Versions.RXPERMISSIONS}"
    const val BINDING_COLLECTION_ADAPTER =
        "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:${Versions.BINDING_COLLECTION_ADAPTER}"
    const val BINDING_COLLECTION_ADAPTER_RECYCLERVIEW =
        "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:${Versions.BINDING_COLLECTION_ADAPTER}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val LOGBACK_ANDROID = "com.github.tony19:logback-android:${Versions.LOGBACK}"
    const val SLF4J = "org.slf4j:slf4j-api:${Versions.SLF4J}"
    const val FLEXBOX = "com.google.android:flexbox:${Versions.FLEXBOX}"
    const val AUTOSZIE = "me.jessyan:autosize:${Versions.AUTOSIZE}"
    const val ONCE = "com.jonathanfinerty.once:once:${Versions.ONCE}"
    const val MULTIDEX = "androidx.multidex:multidex:${Versions.MULTIDEX}"
    const val MMKV = "com.tencent:mmkv-static:${Versions.MMKV}"
    const val AGENTWEB = "com.just.agentweb:agentweb:${Versions.AGENTWEB}"
    const val GUAVA = "com.google.guava:guava:${Versions.GUAVA}"
    const val EASYFLOAT = "com.github.princekin-f:EasyFloat:${Versions.EASYFLOAT}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val AROUTER_API = "com.alibaba:arouter-api:${Versions.AROUTE_API}"
    const val QMUI = "com.qmuiteam:qmui:${Versions.QMUI}"
    const val QMUI_ARCH = "com.qmuiteam:arch:${Versions.QMUI}"
}


/**
 * 注解库
 */
object Kapts {
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val RXHTTP_COMPILER = "com.ljx.rxhttp:rxhttp-compiler:${Versions.RXHTTP}"
    const val AROUTER_COMPILER = "com.alibaba:arouter-compiler:${Versions.AROUTE_COMPILER}"
    const val QMUI_ARCH_COMPILER = "com.qmuiteam:arch-compiler:${Versions.QMUI}"
}