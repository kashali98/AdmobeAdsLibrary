package com.lib.adsimp.domain.model

/**
 * Represents the lifecycle of a single ad load as an immutable state, suitable for
 * driving a UI (MVVM). [T] is the loaded payload type — for native ads this is the
 * concrete ad object (owned by the implementation module), so this type stays generic
 * and SDK-agnostic.
 */
sealed interface AdLoadState<out T> {
    /** Nothing requested yet, or the ad is intentionally hidden/disabled. */
    data object Idle : AdLoadState<Nothing>

    /** A load request is in flight. */
    data object Loading : AdLoadState<Nothing>

    /** The ad loaded successfully. */
    data class Success<T>(val data: T) : AdLoadState<T>

    /** The ad failed to load. */
    data class Failed(val error: AdError) : AdLoadState<Nothing>
}
