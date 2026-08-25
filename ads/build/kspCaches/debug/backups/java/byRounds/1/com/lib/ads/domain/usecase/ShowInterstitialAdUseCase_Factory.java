package com.lib.ads.domain.usecase;

import com.lib.ads.data.interstitial.InterstitialAdsRepository;
import com.lib.adsimp.domain.usecase.GetAdsConfigUseCase;
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
public final class ShowInterstitialAdUseCase_Factory implements Factory<ShowInterstitialAdUseCase> {
  private final Provider<GetAdsConfigUseCase> getAdsConfigProvider;

  private final Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider;

  public ShowInterstitialAdUseCase_Factory(Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider) {
    this.getAdsConfigProvider = getAdsConfigProvider;
    this.interstitialAdsRepositoryProvider = interstitialAdsRepositoryProvider;
  }

  @Override
  public ShowInterstitialAdUseCase get() {
    return newInstance(getAdsConfigProvider.get(), interstitialAdsRepositoryProvider.get());
  }

  public static ShowInterstitialAdUseCase_Factory create(
      Provider<GetAdsConfigUseCase> getAdsConfigProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider) {
    return new ShowInterstitialAdUseCase_Factory(getAdsConfigProvider, interstitialAdsRepositoryProvider);
  }

  public static ShowInterstitialAdUseCase newInstance(GetAdsConfigUseCase getAdsConfig,
      InterstitialAdsRepository interstitialAdsRepository) {
    return new ShowInterstitialAdUseCase(getAdsConfig, interstitialAdsRepository);
  }
}
