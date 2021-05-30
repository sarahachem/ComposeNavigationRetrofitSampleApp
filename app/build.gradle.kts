plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.dogbreeds"
        minSdk = 28
        targetSdk = 30
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
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.compiler:compiler:1.0.0-beta05")
    implementation("androidx.compose.runtime:runtime:1.0.0-beta05")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta05")
    implementation("androidx.compose.ui:ui-viewbinding:1.0.0-beta05")
    implementation("androidx.compose.foundation:foundation:1.0.0-beta05")
    implementation("androidx.compose.material:material:1.0.0-beta05")
    implementation("androidx.compose.ui:ui:1.0.0-beta05")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-beta05")
    implementation("androidx.activity:activity-compose:1.3.0-alpha07")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")
    implementation("androidx.transition:transition:1.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.google.accompanist:accompanist-coil:0.8.1")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.8.1")
    implementation("joda-time:joda-time:2.10.10")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    implementation("androidx.arch.core:core-testing:2.1.0")

    testImplementation("junit:junit:4.+")
    testImplementation("org.mockito:mockito-core:3.7.0")
    testImplementation("org.mockito:mockito-inline:2.13.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
}