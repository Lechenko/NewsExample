
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
   // id("com.google.devtools.ksp")
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
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
}