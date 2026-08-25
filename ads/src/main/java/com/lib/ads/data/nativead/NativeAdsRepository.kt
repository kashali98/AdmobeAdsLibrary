package com.lib.ads.data.nativead

import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.lib.adsimp.domain.model.AdLoadState
import kotlinx.coroutines.flow.Flow

/** Loads Next-Gen native ads and exposes the result as an [AdLoadState] stream. */
interface NativeAdsRepository {
    /**
     * Emits [AdLoadState.Loading] then a single terminal [AdLoadState.Success] or
     * [AdLoadState.Failed]. The loaded [NativeAd] is owned by the collector, which is
     * responsible for eventually calling [NativeAd.destroy].
     */
    fun load(adUnitId: String): Flow<AdLoadState<NativeAd>>
}
