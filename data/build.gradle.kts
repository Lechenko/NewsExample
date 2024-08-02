
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
   // id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.data"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        //multiDexEnabled = true
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
    implementation(project(path = ":portData"))
    implementation(project(path = ":featureLocalStorage"))
    implementation(project(path = ":featureRemoteApi"))
    implementation(Depend.inject)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    implementation(Depend.gson)
    //Log
    implementation(Depend.timberJava)
}
