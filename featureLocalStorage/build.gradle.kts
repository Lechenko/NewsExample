import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.featurelocalstorage"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        val daoName = Versions.dao_name
        buildConfigField("String", "DAO_NAME","\"${daoName}\"")
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
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    //Room
    Depend.room.forEach { implementation(it) }
    kapt(Depend.roomAnnotationProcessor)
    //Log
    implementation(Depend.timberJava)

}
kapt {
    mapDiagnosticLocations = true
}