package com.kashifali.admobadslibrary.activity

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.kashifali.admobadslibrary.R

import com.lib.admoblib.appOpen.AppOpenControl
import com.lib.admoblib.nativeAds.NativeAdPreloader

/**
 * Application for the `legacy` flavor: initializes the classic GMA SDK and the
 * existing :libraryads stack. Contains no Next-Gen / Firebase / Hilt code.
 */
class MyApp : Application() {
    var appOpenManager: AppOpenControl? = null

    override fun onCreate() {
        super.onCreate()
        appOpenManager = AppOpenControl(this, this.getString(R.string.AppOpen))

        // Warm up the classic Google Mobile Ads SDK at startup and preload a native ad.
        MobileAds.initialize(this) {
            NativeAdPreloader.preload(this, getString(R.string.NativeMain))
        }
    }
}
