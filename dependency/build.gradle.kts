import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.dependency"
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
    Depend.dagger.forEach { api(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
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
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}