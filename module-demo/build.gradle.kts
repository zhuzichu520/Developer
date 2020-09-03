plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
        arg("AROUTER_GENERATE_DOC", "enable")
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
        dataBinding=true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to "*.jar"))
    api(project(path = ":library-shared"))
    kapt(Kapts.DAGGER_ANDROID_PROCESSOR)
    kapt(Kapts.DAGGER_COMPILER)
    kapt(Kapts.AROUTER_COMPILER)
}
