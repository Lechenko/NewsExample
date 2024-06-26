import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.presentation"
    buildFeatures.dataBinding = true
    defaultConfig {
        Versions.applicationId
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
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
        }
    }
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
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
    Depend.kotlinDependency.forEach { implementation(it) }
    Depend.lifecycle_viewmodel.forEach { implementation(it) }
    kapt(Depend.lifecycleKpt)

    Depend.supportAndroidLibs.forEach { implementation(it) }
    // Dagger
    Depend.dagger.forEach { implementation(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    implementation(Depend.rxPermission)
    //Log
    implementation(Depend.timberJava)
    implementation(Depend.glide)
    kapt(Depend.glideAnnotationProcessor)
}
kapt {
    mapDiagnosticLocations = true
}
