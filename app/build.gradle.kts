import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
//    lint {
//        abortOnError = false
//    }
//    compileOptions.incremental = false
    namespace = Versions.applicationId
    buildFeatures.dataBinding = true
    buildFeatures.buildConfig = true
//    signingConfigs {
//        create("release") {
//            storeFile = file("..\\key.jks")
//            storePassword = "PASSWORD"
//            keyAlias = "key"
//            keyPassword = "PASSWORD"
//        }
//        getByName("debug"){
//            storeFile = file("..\\key.jks")
//            storePassword = "PASSWORD"
//            keyAlias = "key"
//            keyPassword = "PASSWORD"
//        }
    defaultConfig {
        applicationId = Versions.applicationId
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        compileSdk = Versions.compileSdk
        val appName: String = Versions.appName
        val versionName : String = Versions.versionName
        buildConfigField ("String", "VERSION_NAME","\"${versionName}\"")
        buildConfigField ("String", "APP_NAME","\"${appName}\"")
        setProperty("archivesBaseName", appName + "_" + versionName)
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            manifestPlaceholders["versionCode"] = Versions.versionCode
            manifestPlaceholders["appName"] = Versions.appName
                .plus("_")
                .plus(Versions.versionName)
                .plus("_debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
           // signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            manifestPlaceholders["versionCode"] = Versions.versionCode
            manifestPlaceholders["appName"] =  Versions.appName
                .plus("_")
                .plus(Versions.versionName)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
          //  signingConfig = signingConfigs.getByName("release")
        }

    }
    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
    kotlinExtension.jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
    configurations.all {
        resolutionStrategy {
            force("androidx.core:core-ktx:1.13.1")
        }
    }
    packagingOptions.resources.excludes += setOf("META-INF/*")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(path = ":dependency"))
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
        // implementation("androidx.databinding:databinding-runtime:8.5.0")
}
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}