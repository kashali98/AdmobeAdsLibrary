package com.kashifali.admobadslibrary.activity

import android.app.Application
import com.lib.ads.data.initializer.AdsInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Application for the `nextgen` flavor: enables the Hilt graph used by the Next-Gen
 * Ads module (:ads) and kicks off SDK initialization. Ad unit ids and the AdMob app
 * id come from Firebase Remote Config, so nothing is hardcoded here. Contains no
 * legacy GMA / :libraryads code.
 */
@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var adsInitializer: AdsInitializer

    override fun onCreate() {
        super.onCreate()
        // Initializes on a background thread (required by the Next-Gen SDK).
        adsInitializer.initialize()
    }
}
