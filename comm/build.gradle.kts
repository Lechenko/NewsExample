import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
  //  id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.comm"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        val sharedName = Versions.sharedName
        buildConfigField ("String", "SHARED_NAME","\"${sharedName}\"")
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
 //   implementation(Depend.multidexAndroidLib)
    implementation(Depend.inject)
    //Gson
    implementation(Depend.gson)
}