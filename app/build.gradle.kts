plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "io.appwrite.messagewrite"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.appwrite.messagewrite"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
        }
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
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Material
    implementation(libs.material)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    testAnnotationProcessor(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestAnnotationProcessor(libs.hilt.android.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Appwrite
    implementation(libs.appwrite)

    // Android Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}