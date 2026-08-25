package com.lib.ads.data.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lib.adsimp.domain.config.AdsConfigRepository
import com.lib.adsimp.domain.model.AdsConfig
import com.lib.adsimp.util.AdLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * [AdsConfigRepository] backed by Firebase Remote Config. This is the single place
 * ad unit ids and enable/disable switches are controlled from; everything else in
 * the ads layer reads the resulting [AdsConfig].
 */
@Singleton
class FirebaseAdsConfigRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
) : AdsConfigRepository {

    // Seeded from the in-app Remote Config defaults so callers always have a value.
    private val _config = MutableStateFlow(AdsConfigMapper.fromRemoteConfig(remoteConfig))

    override fun observeConfig(): Flow<AdsConfig> = _config.asStateFlow()

    override fun currentConfig(): AdsConfig = _config.value

    override suspend fun refresh(): AdsConfig = suspendCancellableCoroutine { cont ->
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                AdLogger.w("Remote Config fetch failed: ${task.exception?.message}")
            }
            // Re-map regardless: on failure the last activated/default values remain.
            val config = AdsConfigMapper.fromRemoteConfig(remoteConfig)
            _config.value = config
            AdLogger.d("Ads config updated: $config")
            if (cont.isActive) cont.resume(config)
        }
    }
}
