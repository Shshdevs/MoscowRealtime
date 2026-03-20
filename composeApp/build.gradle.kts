import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.2.0"
    kotlin("native.cocoapods")

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Shared KMP module"
        homepage = "https://example.com"
        ios.deploymentTarget = "14.0"

        framework {
            baseName = "ComposeApp"
            isStatic = true
            export("io.github.mirzemehdi:kmpnotifier:1.6.0")
        }
        podfile = project.file("../iosApp/Podfile")
        pod("FirebaseMessaging")
        pod("YandexMapsMobile") {
            version = "4.26.1-lite"
        }
    }



    sourceSets {
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("org.jetbrains.skiko:skiko:0.7.90")

        }
        androidMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.2")
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.play.services.location)
            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.play.services.map)
            implementation(libs.mapkit.api)

        }
        commonMain.dependencies {
            implementation("org.jetbrains.compose.runtime:runtime:1.10.1")
            implementation("org.jetbrains.compose.foundation:foundation:1.10.1")
            implementation("org.jetbrains.compose.material3:material3:1.9.0")
            implementation("org.jetbrains.compose.ui:ui:1.10.1")
            implementation("org.jetbrains.compose.components:components-resources:1.10.1")
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.1")
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.camerak)
            implementation(libs.image.saver.plugin)
            implementation(libs.haze)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kamel.image.default)
            implementation(libs.kmauth.google)
            implementation(libs.gitlive.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.navigation.compose)
            implementation(libs.compottie)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.lite)
            implementation(libs.compottie.resources)
            api(libs.kmpnotifier)
            version = "0.12.0"
            implementation("io.github.vinceglb:filekit-core:${version}")
            implementation("io.github.vinceglb:filekit-dialogs:${version}")
            implementation("io.github.vinceglb:filekit-dialogs-compose:${version}")
            implementation("io.github.vinceglb:filekit-coil:${version}")
            implementation(libs.storage.kt)
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.1"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")
            implementation("io.insert-koin:koin-compose-viewmodel")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }


}

android {
    namespace = "com.hotelka.moscowrealtime"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hotelka.moscowrealtime"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        mlModelBinding = true
        buildConfig = true
    }
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    defaultConfig {
        buildConfigField(
            "String", "MAPKIT_API_KEY",
            localProperties.getProperty("MAPKIT_API_KEY", " "),
        )
        buildConfigField(
            "String", "WEB_CLIENT_ID",
            localProperties.getProperty("WEB_CLIENT_ID", " "),
        )

        buildConfigField(
            "String", "SUPABASE_URL",
            localProperties.getProperty("SUPABASE_URL", ""),
        )

        buildConfigField(
            "String", "SUPABASE_KEY",
            localProperties.getProperty("SUPABASE_KEY", ""),
        )
        buildConfigField(
            "String", "DEFAULT_PLACEHOLDER",
            localProperties.getProperty("DEFAULT_PLACEHOLDER_URL", ""),
        )
    }

}

dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.google.firebase.firestore)
    debugImplementation("org.jetbrains.compose.ui:ui-tooling:1.10.2")
}

