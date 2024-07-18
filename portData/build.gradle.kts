import org.jetbrains.kotlin.gradle.dsl.kotlinExtension


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.portdata"
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
    }
    kotlin {
        jvmToolchain(Versions.varsionJava)
    }
}

dependencies {
  //  implementation(Depend.multidexAndroidLib)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
}