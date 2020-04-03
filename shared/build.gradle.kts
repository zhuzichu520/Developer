plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {

    compileSdkVersion(Config.compileSdkVersion())

    defaultConfig {
        minSdkVersion(Config.minSdkVersion())
        targetSdkVersion(Config.targetSdkVersion())
        versionCode = Config.versionCode()
        versionName = Config.versionName()
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets {
        getByName("main") {
            res.srcDir("src/main/res")
            jniLibs.srcDir("libs")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dataBinding {
        isEnabled = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to "*.jar"))

    kapt(Dep.kaptDaggerCompiler)
    kapt(Dep.kaptDaggerProcessor)
    kapt(Dep.kaptGlide)
    api(Dep.daggerAndroid)
    api(Dep.daggerAndroidSupport)
    api(Dep.kotlinStadlibJdk8)
    api(Dep.androidxCore)
    api(Dep.androidxAnnotation)
    api(Dep.androidxKtx)
    api(Dep.recyclerview)
    api(Dep.AndroidxExifinterface)
    api(Dep.androidxConstraintlayout)
    api(Dep.androidxNavigationFragment)
    api(Dep.androidxNavigationUi)
    api(Dep.material)
    api(Dep.okhttp)
    api(Dep.retrofit)
    api(Dep.retrofitAdapter)
    api(Dep.retrofitGsonConverter)
    api(Dep.retrofitScalarsConverter)
    api(Dep.loggingInterceptor)
    api(Dep.rxandroid)
    api(Dep.rxbinding)
    api(Dep.rxjava2)
    api(Dep.rxhttp)
    api(Dep.autodispose)
    api(Dep.autodisposeArchcomponents)
    api(Dep.autodisposeAndroid)
    api(Dep.adapter)
    api(Dep.adapterRecyclerview)
    api(Dep.timber)
    api(Dep.swiperefreshlayout)
    api(Dep.autoSzie)
    api(Dep.flexbox)
    api(Dep.rxpermissions)
    api(Dep.logback)
    api(Dep.slf4j)
    api(Dep.once)
    api(Dep.multidex)
    api(Dep.agentweb)
    api(Dep.mmkv)
    api(Dep.dialog)
    api(Dep.dialogFile)
    api(Dep.glideIntegration)
    api(Dep.glide)
    api(Dep.glideTransformations)
    api(project(":libs"))
    api(project(":widget"))
    api(project(":mvvm"))
}