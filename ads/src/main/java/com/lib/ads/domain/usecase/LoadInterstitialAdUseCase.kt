package com.lib.ads.domain.usecase

import com.lib.ads.data.initializer.AdsInitializer
import com.lib.ads.data.interstitial.InterstitialAdsRepository
import com.lib.adsimp.domain.model.AdFormat
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase
import com.lib.adsimp.util.AdLogger
import com.lib.adsimp.util.NetworkMonitor
import javax.inject.Inject

/** Preloads an interstitial if it is enabled, the device is online, and SDK is ready. */
class LoadInterstitialAdUseCase @Inject constructor(
    private val getAdsConfig: GetAdsConfigUseCase,
    private val interstitialAdsRepository: InterstitialAdsRepository,
    private val networkMonitor: NetworkMonitor,
    private val adsInitializer: AdsInitializer,
) {
    suspend operator fun invoke() {
        val config = getAdsConfig.current()
        if (!config.isEnabled(AdFormat.INTERSTITIAL)) {
            AdLogger.d("Interstitial disabled by config; skipping preload")
            return
        }
        if (!networkMonitor.isConnected()) {
            AdLogger.d("Offline; skipping interstitial preload")
            return
        }
        adsInitializer.awaitInitialized()
        interstitialAdsRepository.load(config.adUnitId(AdFormat.INTERSTITIAL))
    }
}
