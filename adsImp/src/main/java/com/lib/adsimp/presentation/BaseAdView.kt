package com.lib.adsimp.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.lib.adsimp.domain.model.AdLoadState

/**
 * Reusable base for any ad container view. It owns the three visual states an ad
 * slot can be in — loading (shimmer), content, and hidden — so concrete views
 * (e.g. the Next-Gen native medium view in :ads) only implement rendering.
 *
 * Subclasses expose their shimmer and content views; this base toggles visibility.
 * It is intentionally SDK-agnostic: it never references an ad object type.
 */
abstract class BaseAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    /** The shimmer/placeholder shown while an ad is loading. */
    protected abstract val shimmerView: ShimmerFrameLayout?

    /** The container that holds the rendered ad once loaded. */
    protected abstract val contentView: View?

    /** Show the loading placeholder. */
    protected fun showLoading() {
        visibility = VISIBLE
        shimmerView?.apply {
            visibility = VISIBLE
            startShimmer()
        }
        contentView?.visibility = INVISIBLE
    }

    /** Reveal the loaded ad content and stop the shimmer. */
    protected fun showContent() {
        visibility = VISIBLE
        shimmerView?.apply {
            stopShimmer()
            visibility = GONE
        }
        contentView?.visibility = VISIBLE
    }

    /** Collapse the whole slot (no ad, disabled, or failed). */
    protected fun hideAd() {
        shimmerView?.stopShimmer()
        contentView?.visibility = GONE
        visibility = GONE
    }

    /**
     * Convenience for MVVM screens: map an [AdLoadState] straight to the view state.
     * [onSuccess] receives the loaded payload so subclasses can bind the concrete ad.
     */
    protected fun <T> renderState(state: AdLoadState<T>, onSuccess: (T) -> Unit) {
        when (state) {
            AdLoadState.Idle -> hideAd()
            AdLoadState.Loading -> showLoading()
            is AdLoadState.Success -> {
                onSuccess(state.data)
                showContent()
            }
            is AdLoadState.Failed -> hideAd()
        }
    }

    open fun onResume() {
        shimmerView?.startShimmer()
    }

    open fun onPause() {
        shimmerView?.stopShimmer()
    }

    open fun onDestroy() {
        shimmerView?.stopShimmer()
    }
}
