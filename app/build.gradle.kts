plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.tic_tac_toe"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tic_tac_toe"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Module
    implementation(project(":core"))
    implementation(project(":common:room"))
    implementation(project(":presentation"))
    implementation(project(":common:design-kit"))

    // Core
    implementation(libs.kotlinx.serialization.json)

    // Jetpack Compose
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(platform(libs.androidx.compose.bom))

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.koin.compose)
    implementation(libs.androidx.koin.compose.navigation)

    // Room
    implementation(libs.androidx.room.runtime)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}
