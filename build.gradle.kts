import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.application") version "8.5.1" apply false
    id("com.android.library") version "8.5.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.23" apply false
}
task("clean") {
    delete(project.layout.buildDirectory)
}
//subprojects {
//    val compileSdkVersion: Int = Versions.compileSdk
//    val minSdkVersion: Int = Versions.minSdk
//    val targetSdkVersion: Int = Versions.targetSdk
//    val buildToolsVersion: String = Versions.buildTools
//    afterEvaluate { subproject ->
//        if (subproject.plugins.hasPlugin("com.android.application") || subproject.plugins.hasPlugin("com.android.library")) {
//            subproject.extensions.configure<com.android.build.gradle.BaseExtension> {
//                compileSdkVersion(compileSdkVersion)
//                buildToolsVersion(buildToolsVersion)
//
//                defaultConfig {
//                    compileSdkVersion(compileSdkVersion)
//                    minSdk = minSdkVersion
//                    targetSdk = targetSdkVersion
//                    multiDexEnabled = true
//                }
//            }
//        }
//    }
//}
//subprojects {
//    val compileSdkVersion: Int = Versions.compileSdk
//    val minSdkVersion: Int = Versions.minSdk
//    val targetSdkVersion: Int = Versions.targetSdk
//    val buildToolsVersion: String = Versions.buildTools
//    // BasePlugin is the common superclass of the AppPlugin and LibraryPlugin which are the plugin classes that "com.android.application" and "com.android.library" apply
//    plugins.withType<BasePlugin> {
//
//        // BaseExtension is the common superclass of the AppExtension and LibraryExtension which are the extension classes registered by the two plugins to the name "android"
//        configure<BaseExtension> {
//            compileSdkVersion(compileSdkVersion)
//            buildToolsVersion(buildToolsVersion)
//            defaultConfig {
//                minSdk = minSdkVersion
//                targetSdk = targetSdkVersion
//                multiDexEnabled = true
//            }
//        }
//    }
//}