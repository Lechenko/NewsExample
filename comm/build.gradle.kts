import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.comm"
    buildFeatures.buildConfig = true
    defaultConfig {
        compileSdk = Versions.compileSdk
        val sharedName = Versions.sharedName
        buildConfigField ("String", "SHARED_NAME","\"${sharedName}\"")
    }
    buildTypes {
        getByName("debug")
        getByName("release")
    }
    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
    kotlinExtension.jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
}

dependencies {
    implementation(Depend.inject)
    //Gson
    implementation(Depend.gson)
}