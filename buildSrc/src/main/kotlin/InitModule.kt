import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/3 3:32 PM
 * since: v 1.0.0
 */

class InitModule(project: Project) {

    init {

        project.apply {
            plugin("com.android.library")
            plugin("org.jetbrains.kotlin.android")
            plugin("org.jetbrains.kotlin.android.extensions")
            plugin("org.jetbrains.kotlin.kapt")
        }


        project.extensions.getByType(AppExtension::class.java).apply {

            compileSdkVersion(Config.compileSdkVersion())


            defaultConfig {
                applicationId = Config.applicationId()
                minSdkVersion(Config.minSdkVersion())
                targetSdkVersion(Config.targetSdkVersion())
                versionCode = Config.versionCode()
                versionName = Config.versionName()
            }

            buildTypes {
                getByName("release") {
                    isShrinkResources = true
                    isMinifyEnabled = true
                    isZipAlignEnabled = true
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

            dataBinding {
                isEnabled = true
            }

            val androidExtensionsExtension =
                project.extensions.getByType(AndroidExtensionsExtension::class.java)
            androidExtensionsExtension.isExperimental = true

        }

        project.dependencies.apply {
            add("implementation", project.fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
            add("api", project(mapOf("path" to ":library-shared")))
            add("kapt", Kapts.DAGGER_ANDROID_PROCESSOR)
            add("kapt", Kapts.DAGGER_COMPILER)
            add("kapt", Kapts.AROUTER_COMPILER)
        }

    }
}