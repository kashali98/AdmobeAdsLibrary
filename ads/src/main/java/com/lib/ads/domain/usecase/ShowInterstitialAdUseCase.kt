package com.lib.ads.domain.usecase

import android.app.Activity
import com.lib.ads.data.interstitial.InterstitialAdsRepository
import com.lib.adsimp.domain.model.AdFormat
import com.lib.adsimp.domain.model.AdPlacement
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase
import javax.inject.Inject

/**
 * Shows the preloaded interstitial, honouring the remote-config frequency cap.
 * [onDismiss] always runs exactly once — after the ad is dismissed, or immediately
 * if the ad is disabled/not ready/throttled — so screen navigation is never blocked.
 */
class ShowInterstitialAdUseCase @Inject constructor(
    private val getAdsConfig: GetAdsConfigUseCase,
    private val interstitialAdsRepository: InterstitialAdsRepository,
) {
    operator fun invoke(
        activity: Activity,
        placement: AdPlacement,
        onDismiss: () -> Unit,
    ) {
        val config = getAdsConfig.current()
        if (!config.isEnabled(AdFormat.INTERSTITIAL)) {
            onDismiss()
            return
        }
        interstitialAdsRepository.show(
            activity = activity,
            minIntervalSeconds = config.interstitialIntervalSeconds,
            placement = placement,
            onDismiss = onDismiss,
        )
    }
}
