package com.lib.ads.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.lib.ads.databinding.ViewNativeMediumAdBinding
import com.lib.adsimp.domain.model.AdLoadState
import com.lib.adsimp.presentation.BaseAdView

/**
 * Drop-in, reusable medium native ad view (Next-Gen SDK). Place it in any layout and
 * drive it from [AdsViewModel] via [render] — or the one-line `bind(...)` extension.
 * It renders the ad through the SDK's [com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdView]
 * so impressions/clicks are tracked correctly per AdMob policy.
 */
class NativeMediumAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BaseAdView(context, attrs, defStyleAttr) {

    private val binding = ViewNativeMediumAdBinding.inflate(LayoutInflater.from(context), this)

    override val shimmerView: ShimmerFrameLayout get() = binding.adsShimmerContainer
    override val contentView: View get() = binding.nativeAdView

    /** Maps an [AdLoadState] to the view: loading → shimmer, success → bind, else hidden. */
    fun render(state: AdLoadState<NativeAd>) {
        renderState(state) { nativeAd -> bind(nativeAd) }
    }

    private fun bind(nativeAd: NativeAd) {
        val adView = binding.nativeAdView

        binding.adHeadline.text = nativeAd.headline
        adView.headlineView = binding.adHeadline

        binding.adBody.applyAssetText(nativeAd.body)
        adView.bodyView = binding.adBody

        binding.adCta.applyAssetText(nativeAd.callToAction)
        adView.callToActionView = binding.adCta

        val iconDrawable = nativeAd.icon?.drawable
        if (iconDrawable != null) {
            binding.adIcon.setImageDrawable(iconDrawable)
            binding.adIcon.visibility = View.VISIBLE
        } else {
            binding.adIcon.visibility = View.GONE
        }
        adView.iconView = binding.adIcon

        binding.adAdvertiser.applyAssetText(nativeAd.advertiser)
        adView.advertiserView = binding.adAdvertiser

        val rating = nativeAd.starRating
        if (rating != null) {
            binding.adStars.rating = rating.toFloat()
            binding.adStars.visibility = View.VISIBLE
        } else {
            binding.adStars.visibility = View.GONE
        }
        adView.starRatingView = binding.adStars

        // Registers the ad and binds the media asset; enables impression/click tracking.
        adView.registerNativeAd(nativeAd, binding.adMedia)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.nativeAdView.destroy()
    }

    private fun android.widget.TextView.applyAssetText(value: String?) {
        if (value.isNullOrBlank()) {
            visibility = View.GONE
        } else {
            text = value
            visibility = View.VISIBLE
        }
    }
}
