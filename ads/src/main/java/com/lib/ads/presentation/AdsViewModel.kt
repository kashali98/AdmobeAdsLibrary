package com.lib.ads.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.lib.ads.data.interstitial.InterstitialAdsRepository
import com.lib.ads.domain.usecase.LoadInterstitialAdUseCase
import com.lib.ads.domain.usecase.LoadNativeMediumAdUseCase
import com.lib.ads.domain.usecase.ShowInterstitialAdUseCase
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.domain.model.AdPlacement
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Single, reusable MVVM entry point for ads. Any screen obtains it (Hilt) and drives
 * ads through plain state + intents, with no knowledge of the AdMob SDK:
 *
 * ```
 * private val adsViewModel: AdsViewModel by viewModels()
 * // Native medium — one line, self-rendering:
 * binding.nativeMediumAd.bind(adsViewModel, this)
 * // Interstitial:
 * adsViewModel.loadInterstitial()
 * adsViewModel.showInterstitial(this, AdPlacement.HOME) { openNextScreen() }
 * ```
 */
@HiltViewModel
class AdsViewModel @Inject constructor(
    private val loadNativeMediumAdUseCase: LoadNativeMediumAdUseCase,
    private val loadInterstitialAdUseCase: LoadInterstitialAdUseCase,
    private val showInterstitialAdUseCase: ShowInterstitialAdUseCase,
    private val getAdsConfigUseCase: GetAdsConfigUseCase,
    interstitialAdsRepository: InterstitialAdsRepository,
) : ViewModel() {

    private val _nativeMediumState = MutableStateFlow<AdLoadState<NativeAd>>(AdLoadState.Idle)
    val nativeMediumState: StateFlow<AdLoadState<NativeAd>> = _nativeMediumState.asStateFlow()

    /** True when a preloaded interstitial is ready to show. */
    val interstitialReady: StateFlow<Boolean> = interstitialAdsRepository.loadState
        .map { it is AdLoadState.Success }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private var currentNativeAd: NativeAd? = null

    /** Load a native medium ad; state is published on [nativeMediumState]. */
    fun loadNativeMedium() {
        viewModelScope.launch {
            loadNativeMediumAdUseCase().collect { state ->
                if (state is AdLoadState.Success) {
                    // Release the previously held ad before replacing it.
                    currentNativeAd?.destroy()
                    currentNativeAd = state.data
                }
                _nativeMediumState.value = state
            }
        }
    }

    /** Preload an interstitial so a later [showInterstitial] is instant. */
    fun loadInterstitial() {
        viewModelScope.launch { loadInterstitialAdUseCase() }
    }

    /**
     * Show the interstitial (respecting the remote frequency cap). [onDismiss] runs
     * exactly once whether or not an ad was actually displayed.
     */
    fun showInterstitial(
        activity: Activity,
        placement: AdPlacement = AdPlacement.GLOBAL,
        onDismiss: () -> Unit = {},
    ) {
        showInterstitialAdUseCase(activity, placement, onDismiss)
    }

    /** Force a Remote Config refresh (e.g. on app resume). */
    fun refreshConfig() {
        viewModelScope.launch { runCatching { getAdsConfigUseCase.refresh() } }
    }

    override fun onCleared() {
        currentNativeAd?.destroy()
        currentNativeAd = null
        super.onCleared()
    }
}
