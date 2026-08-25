package com.kashifali.admobadslibrary.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kashifali.admobadslibrary.databinding.ActivityNextGenAdsBinding
import com.lib.ads.presentation.AdsViewModel
import com.lib.ads.presentation.view.bind
import com.lib.adsimp.domain.model.AdPlacement
import dagger.hilt.android.AndroidEntryPoint

/**
 * Demonstrates how ANY screen consumes the Next-Gen Ads module (:ads):
 *  - a single [AdsViewModel] obtained via Hilt,
 *  - a reusable [com.lib.ads.presentation.view.NativeMediumAdView] wired in one line,
 *  - a preloaded interstitial shown behind a "continue" action.
 *
 * The Activity contains no AdMob SDK code and no ad unit ids — those are controlled
 * entirely by the module + Firebase Remote Config.
 */
@AndroidEntryPoint
class NextGenAdsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextGenAdsBinding
    private val adsViewModel: AdsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextGenAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Native medium ad: loads, renders and cleans up automatically.
        binding.nativeMediumAd.bind(adsViewModel, this)

        // Preload the interstitial so tapping the button is instant.
        adsViewModel.loadInterstitial()
        binding.btnShowInterstitial.setOnClickListener {
            adsViewModel.showInterstitial(this, AdPlacement.DETAIL) {
                // Runs after dismissal (or immediately if disabled/not ready/throttled).
                Toast.makeText(this, "Continue after interstitial", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
