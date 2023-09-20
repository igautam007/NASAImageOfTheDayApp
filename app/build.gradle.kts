import org.jetbrains.kotlin.gradle.utils.`is`

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.dagger.hilt.android")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.gautamgelani.nasaopenapiimages"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gautamgelani.nasaopenapiimages"
        minSdk = 28
        targetSdk = 34
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
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Saved state module for ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")

    // Lifecycles only (without ViewModel or LiveData)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation ("com.google.dagger:hilt-android:2.47")
    kapt ("com.google.dagger:hilt-compiler:2.44")

    //Retrofit libraries
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.3.1")

    //image library
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    //dimension library
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")

    //Logger
    implementation ("com.orhanobut:logger:2.2.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //ViewModels delegation extentensions for activity
    implementation ("androidx.activity:activity-ktx:1.7.2")

    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    implementation ("androidx.hilt:hilt-navigation-fragment:1.0.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}