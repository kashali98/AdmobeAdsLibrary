package com.lib.ads.presentation.view

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lib.ads.presentation.AdsViewModel
import kotlinx.coroutines.launch

/**
 * One-line MVVM wiring: starts a native load (unless [autoLoad] is false), streams
 * [AdsViewModel.nativeMediumState] into this view while the owner is STARTED, and
 * forwards lifecycle events so the shimmer/ad are cleaned up automatically.
 *
 * ```
 * binding.nativeMediumAd.bind(adsViewModel, this)   // Activity or Fragment viewLifecycleOwner
 * ```
 */
fun NativeMediumAdView.bind(
    viewModel: AdsViewModel,
    lifecycleOwner: LifecycleOwner,
    autoLoad: Boolean = true,
) {
    if (autoLoad) viewModel.loadNativeMedium()

    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.nativeMediumState.collect { state -> render(state) }
        }
    }

    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) = this@bind.onResume()
        override fun onPause(owner: LifecycleOwner) = this@bind.onPause()
        override fun onDestroy(owner: LifecycleOwner) = this@bind.onDestroy()
    })
}
