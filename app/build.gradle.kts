plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.arpanapteam.trueid"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.arpanapteam.trueid"
        minSdk = 30
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation("androidx.navigation:navigation-compose:2.9.6")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.ui.text)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    dependencies {

        implementation("androidx.navigation:navigation-compose:2.8.3")
        implementation("androidx.datastore:datastore-preferences:1.1.1")
        implementation("io.github.jan-tennert.supabase:supabase-kt:2.0.0")
        implementation("io.ktor:ktor-client-android:2.3.7")
        // ... aapki purani dependencies yahan hongi ...

        // 👇 YEH SAB ADD KAREIN 👇
        // Supabase BOM (Versions manage karne ke liye)
        implementation(platform("io.github.jan-tennert.supabase:bom:2.1.3"))

        // Supabase Database Module
        implementation("io.github.jan-tennert.supabase:postgrest-kt")

        // Ktor (Network requests ke liye zaroori hai)
        implementation("io.ktor:ktor-client-android:2.3.8")

        // JSON Serialization (Data ko database format mein convert karne ke liye)
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

        implementation("io.coil-kt:coil-compose:2.6.0")
    }
}