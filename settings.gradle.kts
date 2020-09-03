pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("Developer")

include(
    "app",
    "library-shared",
    "library-mvvm",
    "library-libs",
    "library-widget",
    "module-application",
    "module-home",
    "module-category",
    "module-detail",
    "module-easyfloat",
    "module-main",
    "module-me",
    "module-navigation",
    "module-notify",
    "module-route",
    "module-demo"
)
