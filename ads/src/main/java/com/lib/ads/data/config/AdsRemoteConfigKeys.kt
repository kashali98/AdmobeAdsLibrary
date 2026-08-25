package com.lib.ads.data.config

import com.lib.adsimp.domain.model.AdsConfig

/**
 * Firebase Remote Config parameter keys that drive the ads layer. Create matching
 * parameters in the Firebase console to control ads remotely; until then the
 * [AdsRemoteConfigDefaults] (Google test IDs) are used.
 */
object AdsRemoteConfigKeys {
    const val MASTER_ENABLED = "ads_master_enabled"
    const val APPLICATION_ID = "ads_application_id"
    const val NATIVE_MEDIUM_ENABLED = "ads_native_medium_enabled"
    const val NATIVE_MEDIUM_UNIT_ID = "ads_native_medium_unit_id"
    const val INTERSTITIAL_ENABLED = "ads_interstitial_enabled"
    const val INTERSTITIAL_UNIT_ID = "ads_interstitial_unit_id"
    const val INTERSTITIAL_INTERVAL_SECONDS = "ads_interstitial_interval_seconds"
    const val TEST_MODE = "ads_test_mode"
}

/** In-app defaults used by Remote Config before/while remote values are fetched. */
object AdsRemoteConfigDefaults {
    fun asMap(default: AdsConfig = AdsConfig.DEFAULT): Map<String, Any> = mapOf(
        AdsRemoteConfigKeys.MASTER_ENABLED to default.masterEnabled,
        AdsRemoteConfigKeys.APPLICATION_ID to default.applicationId,
        AdsRemoteConfigKeys.NATIVE_MEDIUM_ENABLED to default.nativeMedium.enabled,
        AdsRemoteConfigKeys.NATIVE_MEDIUM_UNIT_ID to default.nativeMedium.adUnitId,
        AdsRemoteConfigKeys.INTERSTITIAL_ENABLED to default.interstitial.enabled,
        AdsRemoteConfigKeys.INTERSTITIAL_UNIT_ID to default.interstitial.adUnitId,
        AdsRemoteConfigKeys.INTERSTITIAL_INTERVAL_SECONDS to default.interstitialIntervalSeconds,
        AdsRemoteConfigKeys.TEST_MODE to default.testMode,
    )
}
