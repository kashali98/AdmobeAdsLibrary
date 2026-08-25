package com.lib.ads.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.lib.ads.BuildConfig
import com.lib.ads.data.config.AdsRemoteConfigDefaults
import com.lib.adsimp.util.AndroidNetworkMonitor
import com.lib.adsimp.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Provides framework singletons (Firebase Remote Config, network monitor). */
@Module
@InstallIn(SingletonComponent::class)
object AdsProvidesModule {

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val settings = remoteConfigSettings {
            // Fetch immediately in debug; throttle in release to respect quotas.
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 3600
        }
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(AdsRemoteConfigDefaults.asMap())
        return remoteConfig
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context,
    ): NetworkMonitor = AndroidNetworkMonitor(context)
}
