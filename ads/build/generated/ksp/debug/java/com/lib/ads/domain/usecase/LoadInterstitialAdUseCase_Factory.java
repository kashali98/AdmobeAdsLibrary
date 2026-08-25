package com.lib.ads.domain.usecase;

import com.lib.ads.data.initializer.AdsInitializer;
import com.lib.ads.data.interstitial.InterstitialAdsRepository;
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase;
import com.lib.adsimp.util.NetworkMonitor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LoadInterstitialAdUseCase_Factory implements Factory<LoadInterstitialAdUseCase> {
  private final Provider<GetAdsConfigUseCase> getAdsConfigProvider;

  private final Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<AdsInitializer> adsInitializerProvider;

  public LoadInterstitialAdUseCase_Factory(Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<AdsInitializer> adsInitializerProvider) {
    this.getAdsConfigProvider = getAdsConfigProvider;
    this.interstitialAdsRepositoryProvider = interstitialAdsRepositoryProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.adsInitializerProvider = adsInitializerProvider;
  }

  @Override
  public LoadInterstitialAdUseCase get() {
    return newInstance(getAdsConfigProvider.get(), interstitialAdsRepositoryProvider.get(), networkMonitorProvider.get(), adsInitializerProvider.get());
  }

  public static LoadInterstitialAdUseCase_Factory create(
      Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<AdsInitializer> adsInitializerProvider) {
    return new LoadInterstitialAdUseCase_Factory(getAdsConfigProvider, interstitialAdsRepositoryProvider, networkMonitorProvider, adsInitializerProvider);
  }

  public static LoadInterstitialAdUseCase newInstance(GetAdsConfigUseCase getAdsConfig,
      InterstitialAdsRepository interstitialAdsRepository, NetworkMonitor networkMonitor,
      AdsInitializer adsInitializer) {
    return new LoadInterstitialAdUseCase(getAdsConfig, interstitialAdsRepository, networkMonitor, adsInitializer);
  }
}
