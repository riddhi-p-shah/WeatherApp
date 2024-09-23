plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.hilt)



//    alias(libs.plugins.kotlin.kapt)
    //id 'kotlin-kapt'
}

android {
    namespace = "com.riddhi.weatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.riddhi.weatherforecast"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //hilt
    implementation(libs.hilt.android)
//    implementation(libs.volley)
//    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
//    ksp(libs.hilt.compiler)
//    kapt "com.google.dagger:hilt-android-compiler:2.28-alpha"

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions)

    implementation( libs.androidx.material.icons.extended) // for full set (larger size)

    implementation (libs.androidx.core)


    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation (libs.converter.gson)

    //navigation
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.hilt.navigation.compose)

    implementation(libs.coil.compose)
    //implementation "com.google.dagger:hilt-android:2.50"
    //kapt "com.google.dagger:hilt-compiler:2.50"


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}