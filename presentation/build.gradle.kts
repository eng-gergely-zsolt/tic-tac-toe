plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.presentation"
    compileSdk = ProjectConfiguration.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectConfiguration.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = ProjectConfiguration.JVM_TARGET
    }
}

dependencies {
    // Jetpack Compose
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(platform(libs.androidx.compose.bom))

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
