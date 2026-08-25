package com.lib.ads.di

import com.lib.ads.data.config.FirebaseAdsConfigRepository
import com.lib.ads.data.interstitial.InterstitialAdsRepository
import com.lib.ads.data.interstitial.InterstitialAdsRepositoryImpl
import com.lib.ads.data.nativead.NativeAdsRepository
import com.lib.ads.data.nativead.NativeAdsRepositoryImpl
import com.lib.adsimp.domain.config.AdsConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Binds ads repository implementations to their SDK-agnostic contracts. */
@Module
@InstallIn(SingletonComponent::class)
abstract class AdsBindsModule {

    @Binds
    @Singleton
    abstract fun bindAdsConfigRepository(
        impl: FirebaseAdsConfigRepository,
    ): AdsConfigRepository

    @Binds
    @Singleton
    abstract fun bindInterstitialAdsRepository(
        impl: InterstitialAdsRepositoryImpl,
    ): InterstitialAdsRepository

    @Binds
    @Singleton
    abstract fun bindNativeAdsRepository(
        impl: NativeAdsRepositoryImpl,
    ): NativeAdsRepository
}
