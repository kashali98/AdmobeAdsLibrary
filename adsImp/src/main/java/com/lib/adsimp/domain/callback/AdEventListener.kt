package com.lib.adsimp.domain.callback

import com.lib.adsimp.domain.model.AdError
import com.lib.adsimp.domain.model.AdEvent

/**
 * Optional, SDK-agnostic listener for ad lifecycle events. Every method has an
 * empty default so callers override only what they need:
 *
 * ```
 * adsViewModel.setNativeListener(object : AdEventListener {
 *     override fun onAdLoaded() { hideLoader() }
 *     override fun onAdFailedToLoad(error: AdError) { hideLoader() }
 * })
 * ```
 */
interface AdEventListener {
    fun onAdLoading() {}
    fun onAdLoaded() {}
    fun onAdFailedToLoad(error: AdError) {}
    fun onAdImpression() {}
    fun onAdClicked() {}
    fun onAdOpened() {}
    fun onAdDismissed() {}
    fun onAdShownFromCache() {}
}

/** Dispatches a single [AdEvent] to the matching [AdEventListener] callback. */
fun AdEventListener.dispatch(event: AdEvent) {
    when (event) {
        AdEvent.Loading -> onAdLoading()
        AdEvent.Loaded -> onAdLoaded()
        is AdEvent.FailedToLoad -> onAdFailedToLoad(event.error)
        AdEvent.Impression -> onAdImpression()
        AdEvent.Clicked -> onAdClicked()
        AdEvent.Opened -> onAdOpened()
        AdEvent.Dismissed -> onAdDismissed()
        AdEvent.ShownFromCache -> onAdShownFromCache()
    }
}
