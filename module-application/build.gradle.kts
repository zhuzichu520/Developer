plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

android {
    compileSdkVersion(Config.compileSdkVersion())

    defaultConfig {
        minSdkVersion(Config.minSdkVersion())
        targetSdkVersion(Config.targetSdkVersion())
        versionCode = Config.versionCode()
        versionName = Config.versionName()
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to "*.jar"))
    implementation(project(path = ":module-category"))
    implementation(project(path = ":module-demo"))
    implementation(project(path = ":module-detail"))
    implementation(project(path = ":module-easyfloat"))
    implementation(project(path = ":module-home"))
    implementation(project(path = ":module-main"))
    implementation(project(path = ":module-me"))
    implementation(project(path = ":module-navigation"))
    implementation(project(path = ":module-notify"))
    implementation(project(path = ":module-route"))
    api(project(path = ":library-shared"))
    kapt(Kapts.DAGGER_ANDROID_PROCESSOR)
    kapt(Kapts.DAGGER_COMPILER)
    kapt(Kapts.AROUTER_COMPILER)
}
