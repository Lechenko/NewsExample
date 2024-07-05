import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    lint {
        abortOnError = false
    }
    namespace = "com.arch.test"
    testOptions.unitTests.isIncludeAndroidResources = true
    buildFeatures.buildConfig = true
    defaultConfig {
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        val apiKey = Versions.api_key
     //   testInstrumentationRunner = "com.arch.test.CustomTestRunner"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField ("String", "API_KEY","\"${apiKey}\"")
        testFunctionalTest = true
        testHandleProfiling = true
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
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
}

tasks.withType<Test> {
    testLogging.showExceptions = true
    useJUnitPlatform {
        includeEngines("spek")
    }
        //useJUnit()
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(path = ":dependency"))
    implementation("androidx.databinding:databinding-runtime:8.5.0")
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
    Depend.testRunner.forEach { androidTestImplementation(it) }
    Depend.testUnit.forEach { testImplementation(it) }
    androidTestImplementation(Depend.testEspresso)
}
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}