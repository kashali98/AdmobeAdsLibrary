package com.lib.adsimp.domain.model

/**
 * The ad formats supported by the ads layer. Kept SDK-agnostic so business logic
 * and configuration can reason about formats without referencing any AdMob type.
 */
enum class AdFormat {
    NATIVE_MEDIUM,
    INTERSTITIAL,
}
