package com.lib.adsimp.domain.usecase

import com.lib.adsimp.domain.config.AdsConfigRepository
import com.lib.adsimp.domain.model.AdsConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Exposes ad configuration to the presentation layer. Thin domain use case so view
 * models depend on an abstraction rather than the Remote Config implementation.
 */
class GetAdsConfigUseCase @Inject constructor(
    private val repository: AdsConfigRepository,
) {
    operator fun invoke(): Flow<AdsConfig> = repository.observeConfig()

    fun current(): AdsConfig = repository.currentConfig()

    suspend fun refresh(): AdsConfig = repository.refresh()
}
