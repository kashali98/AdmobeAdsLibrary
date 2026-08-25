plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    // Next-Gen Ads module: Hilt DI, KSP (Hilt processor), Firebase (Remote Config).
    // Applied module-wide; only the `nextgen` flavor actually uses them.
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.kashifali.admobadslibrary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kashifali.admobadslibrary"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Two isolated ad stacks. They ship different, mutually-incompatible AdMob SDKs
    // (the Next-Gen SDK bundles the classic com.google.android.gms.ads classes), so
    // each flavor links exactly one via its own source set (src/legacy, src/nextgen).
    flavorDimensions += "adsSdk"
    productFlavors {
        create("legacy") {
            dimension = "adsSdk"
            // Classic GMA SDK stack (:libraryads). No Firebase / Hilt / Next-Gen.
        }
        create("nextgen") {
            dimension = "adsSdk"
            // GMA Next-Gen SDK stack (:ads) requires minSdk 24 + google-services.json.
            minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    viewBinding {
        enable = true
    }
}

// The `legacy` flavor doesn't use Firebase, so it must build without google-services.json.
// Disable the Google Services task only for legacy variants (nextgen still requires it).
tasks.configureEach {
    if (name.startsWith("process") && name.contains("Legacy") && name.endsWith("GoogleServices")) {
        enabled = false
    }
}

dependencies {
    // ---- Shared (both flavors) ----
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt is available to all variants; only the `nextgen` flavor defines a graph.
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- legacy flavor: classic GMA SDK stack ----
    "legacyImplementation"(project(":libraryads"))
    "legacyImplementation"(libs.play.services.ads.lite)

    // ---- nextgen flavor: Next-Gen Ads module (+ MVVM helpers) ----
    "nextgenImplementation"(project(":ads"))
    "nextgenImplementation"(libs.androidx.activity.ktx)
    "nextgenImplementation"(libs.kotlinx.coroutines.android)
    "nextgenImplementation"(libs.androidx.lifecycle.runtime.ktx)
}
