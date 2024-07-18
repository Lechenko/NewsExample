import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
android {
    namespace = "com.arch.presentation"

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("debug")
        getByName("release")
    }
    kotlin {
        jvmToolchain(Versions.varsionJava)
    }
}

dependencies {
   // implementation(Depend.multidexAndroidLib)
    implementation(project(path = ":comm"))
    implementation(project(path = ":portDomain"))

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
