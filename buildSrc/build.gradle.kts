logger.lifecycle("初始化buildSrc")
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation(kotlin("gradle-plugin", "1.4.0"))
    implementation(kotlin("android-extensions"))
}
