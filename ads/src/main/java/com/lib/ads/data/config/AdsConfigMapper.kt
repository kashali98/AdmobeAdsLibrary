package com.lib.ads.data.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lib.adsimp.domain.model.AdUnitConfig
import com.lib.adsimp.domain.model.AdsConfig

/** Maps raw Firebase Remote Config values into the SDK-agnostic [AdsConfig] model. */
object AdsConfigMapper {

    fun fromRemoteConfig(rc: FirebaseRemoteConfig): AdsConfig {
        val default = AdsConfig.DEFAULT
        return AdsConfig(
            masterEnabled = rc.getBoolean(AdsRemoteConfigKeys.MASTER_ENABLED),
            applicationId = rc.getString(AdsRemoteConfigKeys.APPLICATION_ID)
                .ifBlank { default.applicationId },
            nativeMedium = AdUnitConfig(
                adUnitId = rc.getString(AdsRemoteConfigKeys.NATIVE_MEDIUM_UNIT_ID)
                    .ifBlank { default.nativeMedium.adUnitId },
                enabled = rc.getBoolean(AdsRemoteConfigKeys.NATIVE_MEDIUM_ENABLED),
            ),
            interstitial = AdUnitConfig(
                adUnitId = rc.getString(AdsRemoteConfigKeys.INTERSTITIAL_UNIT_ID)
                    .ifBlank { default.interstitial.adUnitId },
                enabled = rc.getBoolean(AdsRemoteConfigKeys.INTERSTITIAL_ENABLED),
            ),
            interstitialIntervalSeconds = rc.getLong(AdsRemoteConfigKeys.INTERSTITIAL_INTERVAL_SECONDS)
                .takeIf { it > 0 } ?: default.interstitialIntervalSeconds,
            testMode = rc.getBoolean(AdsRemoteConfigKeys.TEST_MODE),
        )
    }
}
