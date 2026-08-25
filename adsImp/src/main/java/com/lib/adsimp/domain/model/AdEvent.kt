package com.lib.adsimp.domain.model

/**
 * SDK-agnostic ad lifecycle events. Implementations translate their platform
 * callbacks into these so screens can observe ad behaviour without an SDK dependency.
 */
sealed interface AdEvent {
    data object Loading : AdEvent
    data object Loaded : AdEvent
    data class FailedToLoad(val error: AdError) : AdEvent
    data object Impression : AdEvent
    data object Clicked : AdEvent
    data object Opened : AdEvent
    data object Dismissed : AdEvent
    data object ShownFromCache : AdEvent
}
