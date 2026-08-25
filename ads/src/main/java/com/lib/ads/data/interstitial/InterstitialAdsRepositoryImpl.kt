package com.lib.ads.data.interstitial

import android.app.Activity
import android.os.SystemClock
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback
import com.lib.ads.data.mapper.toAdError
import com.lib.adsimp.domain.model.AdEvent
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.domain.model.AdPlacement
import com.lib.adsimp.util.AdLogger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdsRepositoryImpl @Inject constructor() : InterstitialAdsRepository {

    private var interstitialAd: InterstitialAd? = null
    private var lastShownAtMs: Long = 0L

    private val _loadState = MutableStateFlow<AdLoadState<Unit>>(AdLoadState.Idle)
    override val loadState: StateFlow<AdLoadState<Unit>> = _loadState.asStateFlow()

    private val _events = MutableSharedFlow<AdEvent>(extraBufferCapacity = 8)
    override val events: SharedFlow<AdEvent> = _events.asSharedFlow()

    override fun isReady(): Boolean = interstitialAd != null

    override fun load(adUnitId: String) {
        if (interstitialAd != null || _loadState.value is AdLoadState.Loading) {
            AdLogger.d("Interstitial already ready/loading; skipping load")
            return
        }
        _loadState.value = AdLoadState.Loading
        _events.tryEmit(AdEvent.Loading)
        AdLogger.d("Loading interstitial: $adUnitId")

        InterstitialAd.load(
            AdRequest.Builder(adUnitId).build(),
            object : AdLoadCallback<InterstitialAd> {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    _loadState.value = AdLoadState.Success(Unit)
                    _events.tryEmit(AdEvent.Loaded)
                    AdLogger.d("Interstitial loaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    val mapped = adError.toAdError()
                    _loadState.value = AdLoadState.Failed(mapped)
                    _events.tryEmit(AdEvent.FailedToLoad(mapped))
                    AdLogger.w("Interstitial failed to load: ${mapped.message}")
                }
            },
        )
    }

    override fun show(
        activity: Activity,
        minIntervalSeconds: Long,
        placement: AdPlacement,
        onDismiss: () -> Unit,
    ): Boolean {
        val ad = interstitialAd
        if (ad == null) {
            AdLogger.d("Interstitial not ready for '${placement.key}', continuing")
            onDismiss()
            return false
        }

        val now = SystemClock.elapsedRealtime()
        val withinCap = lastShownAtMs != 0L && now - lastShownAtMs < minIntervalSeconds * 1000
        if (withinCap) {
            AdLogger.d("Interstitial throttled for '${placement.key}', continuing")
            onDismiss()
            return false
        }

        ad.adEventCallback = object : InterstitialAdEventCallback {
            override fun onAdShowedFullScreenContent() {
                lastShownAtMs = SystemClock.elapsedRealtime()
                _events.tryEmit(AdEvent.Opened)
            }

            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                _loadState.value = AdLoadState.Idle
                _events.tryEmit(AdEvent.Dismissed)
                onDismiss()
            }

            override fun onAdFailedToShowFullScreenContent(fullScreenContentError: FullScreenContentError) {
                interstitialAd = null
                _loadState.value = AdLoadState.Idle
                _events.tryEmit(AdEvent.FailedToLoad(fullScreenContentError.toAdError()))
                AdLogger.w("Interstitial failed to show: ${fullScreenContentError.message}")
                onDismiss()
            }

            override fun onAdImpression() {
                _events.tryEmit(AdEvent.Impression)
            }

            override fun onAdClicked() {
                _events.tryEmit(AdEvent.Clicked)
            }
        }

        ad.show(activity)
        return true
    }

    override fun clear() {
        interstitialAd = null
        _loadState.value = AdLoadState.Idle
    }
}
