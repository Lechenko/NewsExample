import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.domain"
    defaultConfig {
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        consumerProguardFiles("consumer-rules.pro")
    }
    kapt.includeCompileClasspath = false
    buildTypes {
        getByName("debug") {
            buildConfigField ("Boolean", "TEST_MODE_SCHEDULERS","false")
            isMinifyEnabled = false
            manifestPlaceholders["versionCode"] = Versions.versionCode
            manifestPlaceholders["appName"] = Versions.appName
                .plus("_")
                .plus(Versions.versionName)
                .plus("_debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            buildConfigField ("Boolean", "TEST_MODE_SCHEDULERS","false")
            isMinifyEnabled = true
            manifestPlaceholders["versionCode"] = Versions.versionCode
            manifestPlaceholders["appName"] =  Versions.appName
                .plus("_")
                .plus(Versions.versionName)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("auto_test"){
            isMinifyEnabled = false
            isJniDebuggable = true
            buildConfigField ("Boolean", "TEST_MODE_SCHEDULERS","true")

        }
    }
    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
    kotlinExtension.jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
}

dependencies {
    implementation(project(path = ":comm"))
    implementation(project(path = ":portDomain"))
    implementation(project(path = ":portData"))
    implementation(Depend.inject)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    //Log
    implementation(Depend.timberJava)
}
kapt {
    mapDiagnosticLocations = true
}