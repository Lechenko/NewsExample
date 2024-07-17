import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.data"
    buildFeatures.buildConfig = true
    defaultConfig {
        compileSdk = Versions.compileSdk
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
kapt {
    mapDiagnosticLocations = true
}