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
    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
    defaultConfig {
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        val apiKey = Versions.api_key
       // testInstrumentationRunner = "com.arch.test.CustomTestRunner"

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
//    sourceSets {
//        getByName("main") {
//            java.srcDirs("src/main/java")
//        }
//        getByName("test") {
//            java.srcDirs("src/test/java")
//        }
//        getByName("androidTest") {
//            java.srcDirs("src/androidTest/java")
//        }
//    }
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
    implementation("androidx.test:monitor:1.7.1")
    debugImplementation("androidx.fragment:fragment-testing:1.2.5") {
        exclude(group = "androidx.test", module = "core")
    }
    Depend.supportAndroidLibs.forEach { implementation(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
    Depend.testRunner.forEach { androidTestImplementation(it) }
    Depend.testJUnit.forEach { testImplementation(it) }
    Depend.androidJUnit.forEach { androidTestImplementation(it) }
    androidTestImplementation(Depend.androidTest)
    Depend.testEspresso.forEach { androidTestImplementation(it) }
    kaptTest(Depend.daggerJunit)
    kaptAndroidTest(Depend.daggerJunit)
    androidTestImplementation("com.squareup.rx.idler:rx3-idler:0.11.0")
}
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}