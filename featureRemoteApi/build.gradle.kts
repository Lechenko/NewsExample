import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.featureremoteapi"
    buildFeatures.buildConfig = true
    defaultConfig {
        compileSdk = Versions.compileSdk
        val baseUrl = Versions.base_url
        val apiKey = Versions.api_key
        buildConfigField ("String", "BASE_URL","\"${baseUrl}\"")
        buildConfigField ("String", "API_KEY","\"${apiKey}\"")
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
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    //Retrofit and okHttp
    Depend.okHttpLibraries.forEach { implementation(it) }
    //Log
    implementation(Depend.timberJava)
}