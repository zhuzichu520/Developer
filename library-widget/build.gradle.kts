plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
    kotlin("android")
    kotlin("kapt")
}

group = Dcendents.GROUP
version = Dcendents.VERSION

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        kotlinOptions.freeCompilerArgs =
            kotlinOptions.freeCompilerArgs + listOf("-module-name", "com.hiwitech.android.widget")
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures.dataBinding = true

}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to "*.jar"))
    api(kotlin("stdlib-jdk8"))
    implementation(SupportLibs.ANDROIDX_APPCOMPAT)
    implementation(SupportLibs.ANDROIDX_CORE_KTX)
    implementation(SupportLibs.ANDROIDX_RECYCLERVIEW)
    implementation(SupportLibs.ANDROIDX_CONSTRAINTLAYOUT)
    implementation(Libs.TIMBER)
    implementation(Libs.LOGBACK_ANDROID)
    implementation(Libs.SLF4J)
    implementation(Libs.QMUI)
    implementation(project(":library-libs"))
}
