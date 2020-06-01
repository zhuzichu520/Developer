/**
 * desc
 * author: 朱子楚
 * time: 2020/4/8 2:07 PM
 * since: v 1.0.0
 */

object Dcendents {
    const val GROUP = "com.hiwitech.android"
    const val VERSION = "1.2.1"
}

/**
 * 依赖版本
 */
object Versions {
    const val ANDROIDX_TEST_EXT = "1.1.1"
    const val ANDROIDX_TEST = "1.2.0"
    const val APPCOMPAT = "1.1.0"
    const val EXIFINTERFACE = "1.2.0"
    const val RECYCLERVIEW = "1.1.0"
    const val CORE_KTX = "1.2.0"
    const val ESPRESSO_CORE = "3.2.0"
    const val JUNIT = "4.13"
    const val KTLINT = "0.36.0"

    const val DAGGER = "2.27"

    const val OKHTTP = "4.5.0"

    const val RXJAVA = "2.2.19"
    const val RXANDROID = "2.1.1"
    const val RXBINDING = "3.1.0"
    const val RXHTTP = "2.1.1"
    const val RXPERMISSIONS = "0.10.2"

    const val FASTJSON = "1.1.60.android"

    const val NAVIGATION = "2.2.1"

    const val AUTODISPOSE = "1.4.0"

    const val GLIDE = "4.11.0"
    const val GLIDE_TRANSFORMATIONS = "4.1.0"

    const val MATERIAL = "1.2.0-alpha05"

    const val BINDING_COLLECTION_ADAPTER = "4.0.0"

    const val TIMBER = "4.7.1"
    const val LOGBACK = "2.0.0"
    const val SLF4J = "1.7.30"

    const val SWIPEREFRESHLAYOUT = "1.1.0-beta01"
    const val FLEXBOX = "2.0.1"
    const val CONSTRAINTLAYOUT = "2.0.0-beta4"

    const val MMKV = "1.1.0"
    const val MULTIDEX = "2.0.1"
    const val ONCE = "1.3.0"
    const val AUTOSIZE = "1.2.1"

    const val AGENTWEB = "4.1.2"

    const val GUAVA = "27.0.1-android"
}

/**
 * 插件版本
 */
object BuildPluginsVersion {
    const val AGP = "3.6.2"
    const val DETEKT = "1.7.4"
    const val KOTLIN = "1.3.71"
    const val KTLINT = "9.2.1"
    const val VERSIONS_PLUGIN = "0.28.0"
    const val ANDROID_MAVEN = "2.1"
}

/**
 * Android基础库
 */
object SupportLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    //exifinterface
    const val ANDROIDX_EXIFINTERFACE =
        "androidx.exifinterface:exifinterface:${Versions.EXIFINTERFACE}"
    const val ANDROIDX_RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
    const val ANDROIDX_CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT}"
    const val ANDROIDX_SWIPEREFRESHLAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPEREFRESHLAYOUT}"
}

/**
 * 测试库
 */
object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

/**
 * Android测试库
 */
object AndroidTestingLib {
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

/**
 * 第三方其他库
 */
object Libs {

    //dagger
    const val DAGGER_ANDROID = "com.google.dagger:dagger-android:${Versions.DAGGER}"
    const val DAGGER_ANDROID_SUPPORT = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"

    //fastjson
    const val FASTJSON = "com.alibaba:fastjson:${Versions.FASTJSON}"

    //navigation
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    //autodispose
    const val AUTODISPOSE = "com.uber.autodispose:autodispose:${Versions.AUTODISPOSE}"
    const val AUTODISPOSE_ANDROID =
        "com.uber.autodispose:autodispose-android:${Versions.AUTODISPOSE}"
    const val AUTODISPOSE_ANDROID_ARCHCOMPONENTS =
        "com.uber.autodispose:autodispose-android-archcomponents:${Versions.AUTODISPOSE}"

    //glide
    const val GLIDE_OKHTTP =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE}"
    const val GLIDE =
        "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_TRANSFORMATIONS =
        "jp.wasabeef:glide-transformations:${Versions.GLIDE_TRANSFORMATIONS}"

    //material
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    //okhttp
    const val OKHTTP =
        "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"

    //rx
    const val RXHTTP = "com.rxjava.rxhttp:rxhttp:${Versions.RXHTTP}"
    const val RXJAVA = "io.reactivex.rxjava2:rxjava:${Versions.RXJAVA}"
    const val RXANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.RXANDROID}"
    const val RXBINDING_CORE = "com.jakewharton.rxbinding3:rxbinding-core:${Versions.RXBINDING}"
    const val RXPERMISSIONS = "com.github.tbruyelle:rxpermissions:${Versions.RXPERMISSIONS}"

    //adapter
    const val BINDING_COLLECTION_ADAPTER =
        "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:${Versions.BINDING_COLLECTION_ADAPTER}"
    const val BINDING_COLLECTION_ADAPTER_RECYCLERVIEW =
        "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:${Versions.BINDING_COLLECTION_ADAPTER}"

    //log
    const val TIMBER =
        "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val LOGBACK_ANDROID = "com.github.tony19:logback-android:${Versions.LOGBACK}"
    const val SLF4J = "org.slf4j:slf4j-api:${Versions.SLF4J}"

    //ui
    const val FLEXBOX = "com.google.android:flexbox:${Versions.FLEXBOX}"

    //autosize
    const val AUTOSZIE = "me.jessyan:autosize:${Versions.AUTOSIZE}"
    //once
    const val ONCE = "com.jonathanfinerty.once:once:${Versions.ONCE}"
    //multidex
    const val MULTIDEX = "androidx.multidex:multidex:${Versions.MULTIDEX}"
    //mmkv
    const val MMKV = "com.tencent:mmkv-static:${Versions.MMKV}"
    //agentweb
    const val AGENTWEB = "com.just.agentweb:agentweb:${Versions.AGENTWEB}"
    //guava
    const val GUAVA = "com.google.guava:guava:${Versions.GUAVA}"
}


/**
 * 注解库
 */
object Kapts {
    //dagger
    const val DAGGER_ANDROID_PROCESSOR = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"

    //glide
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    //rx
    const val RXHTTP_COMPILER = "com.rxjava.rxhttp:rxhttp-compiler:${Versions.RXHTTP}"
}