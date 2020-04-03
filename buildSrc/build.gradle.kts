plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    gradleApi()
    localGroovy()
}