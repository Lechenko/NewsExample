import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.arch.test"
    testOptions.unitTests.isIncludeAndroidResources = true
    defaultConfig {
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        val apiKey = Versions.api_key
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField ("String", "API_KEY","\"${apiKey}\"")
        testFunctionalTest = true
        testHandleProfiling = true
      //  testApplicationId = "test_news"
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
        }
        create("auto_test"){
            isMinifyEnabled = false
            isJniDebuggable = true
        }
    }

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
    kotlinExtension.jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.varsionJava))
    }
}
tasks.withType<Test> {
    testLogging.showExceptions = true
    useJUnitPlatform {
        includeEngines("spek")
    }
   // useJUnit()
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(path = ":dependency"))
    api(project(path = ":comm"))
    implementation(project(path = ":portData"))
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":portDomain"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":featureLocalStorage"))
    implementation(project(path = ":featureRemoteApi"))
    Depend.kotlinDependency.forEach { implementation(it) }
    // Dagger
    Depend.dagger.forEach { implementation(it) }
    Depend.daggerAnnotationProcessor.forEach { kapt(it) }
    implementation(Depend.rxPermission)
    //RX
    Depend.rxAndroid.forEach { implementation(it) }
    implementation(Depend.gson)
    Depend.testRunner.forEach { androidTestImplementation(it) }
    Depend.testUnit.forEach { testImplementation(it) }
    androidTestImplementation(Depend.testEspresso)
    implementation(Depend.timberJava)
}
kapt {
    mapDiagnosticLocations = true // include the Kotlin files into error reports
}