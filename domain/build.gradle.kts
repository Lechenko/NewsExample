import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.domain"
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
    implementation(project(path = ":portDomain"))
    implementation(project(path = ":portData"))
    implementation(Depend.inject)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    //Log
    implementation(Depend.timberJava)
}
kapt {
    mapDiagnosticLocations = true
}