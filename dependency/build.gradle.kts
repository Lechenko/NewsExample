
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.dependency"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        multiDexEnabled = true
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
    api(Depend.multidexAndroidLib)
    Depend.dagger.forEach { api(it) }
    Depend.daggerAnnotationProcessor.forEach {kapt(it) }
    api(Depend.rxPermission)
    api(Depend.timberJava)
    Depend.rxAndroid.forEach { api(it) }
    //Module
    api(project(path = ":comm"))
    api(project(path = ":portData"))
    api(project(path = ":data"))
    api(project(path = ":domain"))
    api(project(path = ":presentation"))
    api(project(path = ":portDomain"))
    api(project(path = ":domain"))
    api(project(path = ":featureLocalStorage"))
    api(project(path = ":featureRemoteApi"))
}
