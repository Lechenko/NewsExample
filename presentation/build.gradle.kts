
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
android {
    namespace = "com.arch.presentation"

    buildFeatures {
        buildConfig = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    defaultConfig {
        minSdk = Versions.minSdk
        compileSdk = Versions.compileSdk
        vectorDrawables.useSupportLibrary = true
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
    implementation(project(path = ":comm"))
    implementation(project(path = ":portDomain"))
    implementation(platform(Depend.composeBom))
  //  implementation("androidx.lifecycle:lifecycle-runtime-android:2.8.4")
    Depend.compose.forEach { implementation(it) }
    androidTestImplementation(platform(Depend.composeBom))
    Depend.lifecycle_viewmodel.forEach { implementation(it) }
    kapt(Depend.lifecycleKpt)
    Depend.supportAndroidLibs.forEach { implementation(it) }
    // Dagger
    Depend.dagger.forEach { implementation(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    implementation(Depend.rxPermission)
    //Log
    implementation(Depend.timberJava)
    implementation(Depend.glide)
    kapt(Depend.glideAnnotationProcessor)
    //kapt("com.android.databinding:compiler:7.0.0")
}
kapt {
    mapDiagnosticLocations = true
}
