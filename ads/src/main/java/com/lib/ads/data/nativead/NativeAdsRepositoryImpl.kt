package com.lib.ads.data.nativead

import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoader
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoaderCallback
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdRequest
import com.lib.ads.data.mapper.toAdError
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.util.AdLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeAdsRepositoryImpl @Inject constructor() : NativeAdsRepository {

    override fun load(adUnitId: String): Flow<AdLoadState<NativeAd>> = callbackFlow {
        trySend(AdLoadState.Loading)
        AdLogger.d("Loading native ad: $adUnitId")

        val request = NativeAdRequest
            .Builder(adUnitId, listOf(NativeAd.NativeAdType.NATIVE))
            .build()

        val callback = object : NativeAdLoaderCallback {
            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                AdLogger.d("Native ad loaded")
                trySend(AdLoadState.Success(nativeAd))
                close()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                val mapped = adError.toAdError()
                AdLogger.w("Native ad failed to load: ${mapped.message}")
                trySend(AdLoadState.Failed(mapped))
                close()
            }
        }

        NativeAdLoader.load(request, callback)

        awaitClose { /* No listener to detach; ownership of the ad passed to collector. */ }
    }
}
