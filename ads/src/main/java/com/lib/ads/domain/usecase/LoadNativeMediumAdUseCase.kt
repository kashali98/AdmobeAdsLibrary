package com.lib.ads.domain.usecase

import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.lib.ads.data.initializer.AdsInitializer
import com.lib.ads.data.nativead.NativeAdsRepository
import com.lib.adsimp.domain.model.AdError
import com.lib.adsimp.domain.model.AdFormat
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase
import com.lib.adsimp.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Loads a native medium ad, applying the business rules that belong to the ads
 * domain — remote-config enablement, connectivity, and SDK readiness — before
 * delegating to the repository. Screens never see any of this.
 */
class LoadNativeMediumAdUseCase @Inject constructor(
    private val getAdsConfig: GetAdsConfigUseCase,
    private val nativeAdsRepository: NativeAdsRepository,
    private val networkMonitor: NetworkMonitor,
    private val adsInitializer: AdsInitializer,
) {
    operator fun invoke(): Flow<AdLoadState<NativeAd>> = flow {
        val config = getAdsConfig.current()
        if (!config.isEnabled(AdFormat.NATIVE_MEDIUM)) {
            emit(AdLoadState.Failed(AdError.Disabled))
            return@flow
        }
        if (!networkMonitor.isConnected()) {
            emit(AdLoadState.Failed(AdError.NetworkUnavailable))
            return@flow
        }
        // Show the loading state right away (covers SDK cold-start) before the request.
        emit(AdLoadState.Loading)
        adsInitializer.awaitInitialized()
        emitAll(nativeAdsRepository.load(config.adUnitId(AdFormat.NATIVE_MEDIUM)))
    }
}
