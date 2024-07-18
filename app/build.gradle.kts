
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
android {
    val appName: String = Versions.appName
    val versionName : String = Versions.versionName
    lint {
        abortOnError = false
        absolutePaths = false
        lintConfig =  file("lint.xml")
    }
    namespace = Versions.applicationId
    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
    signingConfigs {
        create("release") {
            storeFile = file("..\\key_test.jks")
            storePassword = "Qwerty1qaz"
            keyAlias = "my_key"
            keyPassword = "Qwerty1qaz"
        }
    }
    defaultConfig {
        applicationId = Versions.applicationId
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        compileSdk = Versions.compileSdk
        multiDexEnabled = true
        buildConfigField ("String", "VERSION_NAME","\"${versionName}\"")
        buildConfigField ("String", "APP_NAME","\"${appName}\"")
        setProperty("archivesBaseName", appName + "_" + versionName)
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            manifestPlaceholders["appName"] = appName
                .plus("_")
                .plus(versionName)
                .plus("_debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            manifestPlaceholders["appName"] =  appName
                .plus("_")
                .plus(versionName)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

    }
//    java.toolchain {
//        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
//    }
//    kotlinExtension.jvmToolchain {
//        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
//    }
    kotlin {
        jvmToolchain(Versions.varsionJava)
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
    Depend.kotlinDependency.forEach { implementation(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
}
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}