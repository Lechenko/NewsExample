
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.featureremoteapi"
    buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = Versions.minSdk
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
    kotlin {
        jvmToolchain(Versions.varsionJava)
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