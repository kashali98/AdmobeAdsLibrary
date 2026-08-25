plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

/**
 * :ads — Google AdMob Next-Gen implementation.
 *
 * ALL Next-Gen Mobile Ads SDK code lives here (data + domain use cases + MVVM
 * presentation + Hilt wiring). It implements the SDK-agnostic contracts declared
 * in :adsImp and is driven entirely by Firebase Remote Config, so screens depend
 * only on a view model / reusable views — never on an ad SDK type.
 */
android {
    namespace = "com.lib.ads"
    compileSdk = 36

    defaultConfig {
        // GMA Next-Gen SDK requires minSdk 24 and compileSdk 35+.
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
        buildConfig = true
    }
}

dependencies {
    // Reusable, SDK-agnostic contracts. Exposed as `api` because :adsImp types are
    // part of :ads's public surface (e.g. NativeMediumAdView extends BaseAdView,
    // AdsViewModel APIs use AdPlacement/AdLoadState), so consumers must see them.
    api(project(":adsImp"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.shimmer)

    // Google AdMob Next-Gen Mobile Ads SDK.
    implementation(libs.ads.mobile.sdk)

    // Dependency injection (Clean Architecture wiring).
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutines + MVVM.
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Firebase Remote Config (ad configuration source).
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
