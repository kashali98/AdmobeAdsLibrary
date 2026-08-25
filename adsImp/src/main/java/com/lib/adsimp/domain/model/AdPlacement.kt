package com.lib.adsimp.domain.model

/**
 * Identifies *where* an ad is requested/shown (a screen or a logical slot).
 *
 * Placement is a lightweight, open-ended key so any screen in any app can define
 * its own without touching the ads modules. It is used for logging and for
 * per-placement throttling (e.g. interstitial frequency capping).
 */
@JvmInline
value class AdPlacement(val key: String) {
    companion object {
        val GLOBAL = AdPlacement("global")
        val HOME = AdPlacement("home")
        val EXIT = AdPlacement("exit")
        val DETAIL = AdPlacement("detail")
    }
}
