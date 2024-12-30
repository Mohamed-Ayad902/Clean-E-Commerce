plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.mayad7474.cleane_commerce"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mayad7474.cleane_commerce"
        minSdk = 24
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // lottie with compose
    implementation(libs.lottie)


    // Hilt - dependency injection
    implementation(libs.hilt.android)
    // hilt kapt - annotation processor — hilt ksp is in alpha
    kapt(libs.hilt.compiler)

    // Retrofit - make type safe requests
    implementation(libs.retrofit)
    // okhttp - fire http calls
    implementation(libs.okhttp)
    // logging interceptor - log calls for debugging
    implementation(libs.logging.interceptor)
    // converter - make retrofit use serialization json
    implementation(libs.converter.kotlinx.serialization)
    // JSON - serialize Kotlin objects <-> JSON
    implementation(libs.kotlinx.serialization.json)

    // Coil - asynchronous image loading
    implementation(libs.coil.compose)

    // datastore - make type safe local storage
    implementation(libs.androidx.datastore)

    // room - local database
    implementation(libs.androidx.room.runtime)
    // room ksp - annotation processor
    ksp(libs.androidx.room.compiler)
    // room - coroutine support
    implementation(libs.androidx.room.ktx)

    // kotlin coroutines tests
    testImplementation(libs.kotlinx.coroutines.test)

    // collect as state with lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    // compose nav host & type safe navigation
    implementation(libs.androidx.navigation.compose)
    // hilt view model injection
    implementation(libs.androidx.hilt.navigation.compose)


}