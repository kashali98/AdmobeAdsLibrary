package com.lib.ads.data.initializer

import android.content.Context
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig
import com.lib.adsimp.domain.config.AdsConfigRepository
import com.lib.adsimp.util.AdLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Initializes the GMA Next-Gen SDK exactly once, on a background thread (required by
 * the SDK to avoid ANRs). The AdMob application id comes from Remote Config, so the
 * app never hardcodes it. Ad loaders wait on [awaitInitialized] before requesting ads.
 */
@Singleton
class AdsInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val configRepository: AdsConfigRepository,
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val started = AtomicBoolean(false)

    private val _initialized = MutableStateFlow(false)
    val initialized: StateFlow<Boolean> = _initialized.asStateFlow()

    /** Idempotent. Safe to call from Application.onCreate. */
    fun initialize() {
        if (!started.compareAndSet(false, true)) return
        scope.launch {
            // Pull the freshest config first so we initialize with the correct app id.
            val config = runCatching { configRepository.refresh() }
                .getOrDefault(configRepository.currentConfig())

            MobileAds.initialize(
                context,
                InitializationConfig.Builder(config.applicationId).build(),
            ) {
                _initialized.value = true
                AdLogger.d("GMA Next-Gen SDK initialized (appId=${config.applicationId})")
            }
        }
    }

    /** Suspends until the SDK is initialized, kicking off initialization if needed. */
    suspend fun awaitInitialized() {
        if (_initialized.value) return
        initialize()
        _initialized.first { it }
    }
}
