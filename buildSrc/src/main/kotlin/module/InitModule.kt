package module

import Config
import Kapts
import Log
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/3 3:32 PM
 * since: v 1.0.0
 */

class InitModule(private val project: Project) {

    init {

        val isMainModule = isMainModule()

        project.apply {
            if (isMainModule) {
                Log.l(project.name, "主module")
                plugin("com.android.application")
            } else {
                plugin("com.android.library")
                Log.l(project.name, "不是主module")
            }
            plugin("org.jetbrains.kotlin.android")
            plugin("org.jetbrains.kotlin.android.extensions")
            plugin("org.jetbrains.kotlin.kapt")
        }

        project.extensions.getByType(KaptExtension::class.java).apply {
            this.arguments {
                arg("AROUTER_MODULE_NAME", project.name)
                arg("AROUTER_GENERATE_DOC", "enable")
            }
        }

        if (isMainModule) {
            initApplication()
        } else {
            initLibrary()
        }

        project.dependencies.apply {
            add("implementation", project.fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
            add("api", project(mapOf("path" to ":library-shared")))
            add("kapt", Kapts.AROUTER_COMPILER)
        }

    }

    /**
     * 初始化Module是Application的
     */
    private fun initApplication() {
        project.extensions.getByType(AppExtension::class.java).apply {

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
            }

            val kotlinJvmOptions =
                (this as ExtensionAware).extensions.getByType(KotlinJvmOptions::class.java)
            kotlinJvmOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

            @Suppress("UnstableApiUsage")
            buildFeatures.dataBinding = true

            val androidExtensionsExtension =
                project.extensions.getByType(AndroidExtensionsExtension::class.java)
            androidExtensionsExtension.isExperimental = true

            sourceSets["main"].apply {
                manifest.srcFile(
                    "src/main/app/AndroidManifest.xml"
                )
            }
        }
    }

    /**
     * 初始化Module是Library
     */
    private fun initLibrary() {
        project.extensions.getByType(LibraryExtension::class.java).apply {

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

            val kotlinJvmOptions =
                (this as ExtensionAware).extensions.getByType(KotlinJvmOptions::class.java)
            kotlinJvmOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

            @Suppress("UnstableApiUsage")
            buildFeatures.dataBinding = true

            val androidExtensionsExtension =
                project.extensions.getByType(AndroidExtensionsExtension::class.java)
            androidExtensionsExtension.isExperimental = true

            sourceSets["main"].apply {
                manifest.srcFile(
                    "src/main/module/AndroidManifest.xml"
                )
            }
        }
    }

    /**
     * 是否是主Module
     */
    private fun isMainModule(): Boolean {
        return  Config.isAppModule(project.name)
    }

}