package com.lib.ads.domain.usecase;

import com.lib.ads.data.initializer.AdsInitializer;
import com.lib.ads.data.nativead.NativeAdsRepository;
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
public final class LoadNativeMediumAdUseCase_Factory implements Factory<LoadNativeMediumAdUseCase> {
  private final Provider<GetAdsConfigUseCase> getAdsConfigProvider;

  private final Provider<NativeAdsRepository> nativeAdsRepositoryProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<AdsInitializer> adsInitializerProvider;

  public LoadNativeMediumAdUseCase_Factory(Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<NativeAdsRepository> nativeAdsRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<AdsInitializer> adsInitializerProvider) {
    this.getAdsConfigProvider = getAdsConfigProvider;
    this.nativeAdsRepositoryProvider = nativeAdsRepositoryProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.adsInitializerProvider = adsInitializerProvider;
  }

  @Override
  public LoadNativeMediumAdUseCase get() {
    return newInstance(getAdsConfigProvider.get(), nativeAdsRepositoryProvider.get(), networkMonitorProvider.get(), adsInitializerProvider.get());
  }

  public static LoadNativeMediumAdUseCase_Factory create(
      Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<NativeAdsRepository> nativeAdsRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<AdsInitializer> adsInitializerProvider) {
    return new LoadNativeMediumAdUseCase_Factory(getAdsConfigProvider, nativeAdsRepositoryProvider, networkMonitorProvider, adsInitializerProvider);
  }

  public static LoadNativeMediumAdUseCase newInstance(GetAdsConfigUseCase getAdsConfig,
      NativeAdsRepository nativeAdsRepository, NetworkMonitor networkMonitor,
      AdsInitializer adsInitializer) {
    return new LoadNativeMediumAdUseCase(getAdsConfig, nativeAdsRepository, networkMonitor, adsInitializer);
  }
}
