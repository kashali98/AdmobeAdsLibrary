package com.lib.adsimp.domain.config

import com.lib.adsimp.domain.model.AdsConfig
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the source of ad configuration. The Next-Gen module (:ads) provides a
 * Firebase Remote Config backed implementation, but any source can satisfy this
 * interface, keeping configuration decoupled from the ad SDK and from business logic.
 */
interface AdsConfigRepository {

    /** Emits the active [AdsConfig] and re-emits whenever new remote values activate. */
    fun observeConfig(): Flow<AdsConfig>

    /** The currently active configuration (never blocks; falls back to defaults). */
    fun currentConfig(): AdsConfig

    /** Fetches and activates the latest configuration, returning the new snapshot. */
    suspend fun refresh(): AdsConfig
}
