package com.lib.adsimp.domain.model

/**
 * SDK-agnostic ad error. Concrete implementations (e.g. the Next-Gen SDK layer)
 * map their platform error into this type so callers never depend on an SDK class.
 */
data class AdError(
    val code: Int = UNKNOWN_CODE,
    val message: String = "Unknown ad error",
    val domain: String? = null,
) {
    companion object {
        const val UNKNOWN_CODE = -1
        const val NO_NETWORK_CODE = -100
        const val DISABLED_CODE = -101
        const val NOT_READY_CODE = -102
        const val THROTTLED_CODE = -103

        val NetworkUnavailable = AdError(NO_NETWORK_CODE, "No network connection")
        val Disabled = AdError(DISABLED_CODE, "Ad disabled by remote config")
        val NotReady = AdError(NOT_READY_CODE, "Ad not loaded yet")
        val Throttled = AdError(THROTTLED_CODE, "Ad suppressed by frequency cap")
    }
}
