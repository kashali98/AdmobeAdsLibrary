package com.lib.ads.data.interstitial

import android.app.Activity
import com.lib.adsimp.domain.model.AdEvent
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.domain.model.AdPlacement
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Loads, caches and shows a single Next-Gen interstitial ad. Frequency capping and
 * the "show or continue" contract live here so screens stay trivial.
 */
interface InterstitialAdsRepository {

    /** Load state of the cached interstitial (Idle / Loading / Success / Failed). */
    val loadState: StateFlow<AdLoadState<Unit>>

    /** Fire-and-forget lifecycle events for observers that want them. */
    val events: SharedFlow<AdEvent>

    /** True when an ad is cached and ready to show. */
    fun isReady(): Boolean

    /** Request an interstitial for [adUnitId]. No-op if one is already loading/ready. */
    fun load(adUnitId: String)

    /**
     * Shows the cached ad if ready and not throttled by [minIntervalSeconds].
     * [onDismiss] is invoked when the ad is dismissed OR immediately if the ad can't
     * be shown, so the caller's flow (e.g. navigation) always continues exactly once.
     *
     * @return true if the ad was shown, false if the caller should proceed now.
     */
    fun show(
        activity: Activity,
        minIntervalSeconds: Long,
        placement: AdPlacement,
        onDismiss: () -> Unit,
    ): Boolean

    /** Drops any cached ad. */
    fun clear()
}
