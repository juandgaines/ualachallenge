plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.juandgaines.challengeplaces"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.juandgaines.challengeplaces"
        minSdk = 26
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


    implementation(libs.bundles.android.presentation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.adaptive.navigation.android)
    debugImplementation(libs.bundles.android.compose.debug)

    implementation(libs.bundles.maps)
    implementation(libs.kotlinx.coroutines.core)
    //Navigation
    implementation(libs.androidx.navigation.compose)

    implementation(libs.dagger.hilt.navigation.compose)
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.networking)

    implementation(libs.bundles.database)
    ksp(libs.room.compiler)


    // Test dependencies
    testImplementation(libs.bundles.unit.testing)

    // Android Test dependencies
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))

}