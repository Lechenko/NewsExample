
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
   // id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.domain"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
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
  //  implementation(Depend.multidexAndroidLib)
    implementation(project(path = ":comm"))
    implementation(project(path = ":portDomain"))
    implementation(project(path = ":portData"))
    implementation(Depend.inject)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    //Log
    implementation(Depend.timberJava)
}
