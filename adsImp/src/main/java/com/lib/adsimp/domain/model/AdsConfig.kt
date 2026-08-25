package com.lib.adsimp.domain.model

/** Configuration for a single ad unit: its id and whether it is switched on. */
data class AdUnitConfig(
    val adUnitId: String,
    val enabled: Boolean,
)

/**
 * The full, SDK-agnostic ads configuration. This is the shape produced from
 * Firebase Remote Config (in :ads) and consumed by use cases and view models.
 *
 * A [DEFAULT] using Google's official AdMob test IDs is provided so the module is
 * fully functional before any remote values are fetched.
 */
data class AdsConfig(
    /** Global kill-switch for every ad in the app. */
    val masterEnabled: Boolean,
    /** AdMob application id passed to the SDK initializer. */
    val applicationId: String,
    val nativeMedium: AdUnitConfig,
    val interstitial: AdUnitConfig,
    /** Minimum gap between two interstitials (frequency cap), in seconds. */
    val interstitialIntervalSeconds: Long,
    /** When true the SDK is put in test mode (test ads on the current device). */
    val testMode: Boolean,
) {
    fun unitFor(format: AdFormat): AdUnitConfig = when (format) {
        AdFormat.NATIVE_MEDIUM -> nativeMedium
        AdFormat.INTERSTITIAL -> interstitial
    }

    /** True only when both the master switch and the format's own switch are on. */
    fun isEnabled(format: AdFormat): Boolean = masterEnabled && unitFor(format).enabled

    fun adUnitId(format: AdFormat): String = unitFor(format).adUnitId

    companion object {
        // Google's official AdMob sample/test IDs. Safe to ship as defaults because
        // they only ever serve test ads; real IDs arrive via Remote Config.
        const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"
        const val TEST_NATIVE_ID = "ca-app-pub-3940256099942544/2247696110"
        const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"

        val DEFAULT = AdsConfig(
            masterEnabled = true,
            applicationId = TEST_APP_ID,
            nativeMedium = AdUnitConfig(TEST_NATIVE_ID, enabled = true),
            interstitial = AdUnitConfig(TEST_INTERSTITIAL_ID, enabled = true),
            interstitialIntervalSeconds = 15,
            testMode = true,
        )
    }
}
