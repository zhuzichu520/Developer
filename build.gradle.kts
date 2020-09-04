import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

Config.init(project)

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(ClassPaths.androidBuildTools)
        classpath(ClassPaths.kotlinGradlePlugin)
        classpath(ClassPaths.manesPlugin)
        classpath(ClassPaths.dcendentsPlugin)
    }

}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://dl.bintray.com/umsdk/release") }
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String) = "^[0-9,.v-]+(-r)?$".toRegex().matches(version).not()
