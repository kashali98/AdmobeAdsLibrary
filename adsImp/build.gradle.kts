plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

/**
 * :adsImp — reusable, SDK-agnostic ad layer.
 *
 * Holds the shared contracts every ad implementation reuses: domain models
 * (ad formats, config, load state, events), repository interfaces, use cases,
 * and base UI components. It deliberately does NOT depend on any AdMob SDK, so
 * the Next-Gen implementation lives entirely in :ads and business logic never
 * touches an ad SDK type directly.
 */
android {
    namespace = "com.lib.adsimp"
    compileSdk = 36

    defaultConfig {
        // GMA Next-Gen SDK (consumed downstream in :ads) requires minSdk 24.
        minSdk = 24

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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.shimmer)

    // Coroutines Flow is part of the public contract (config/state streams).
    api(libs.kotlinx.coroutines.android)
    // @Inject on constructors so :ads can wire these into the Hilt graph.
    api(libs.javax.inject)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
