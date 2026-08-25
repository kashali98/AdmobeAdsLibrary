package com.lib.ads.presentation;

import com.lib.ads.data.interstitial.InterstitialAdsRepository;
import com.lib.ads.domain.usecase.LoadInterstitialAdUseCase;
import com.lib.ads.domain.usecase.LoadNativeMediumAdUseCase;
import com.lib.ads.domain.usecase.ShowInterstitialAdUseCase;
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
public final class AdsViewModel_Factory implements Factory<AdsViewModel> {
  private final Provider<LoadNativeMediumAdUseCase> loadNativeMediumAdUseCaseProvider;

  private final Provider<LoadInterstitialAdUseCase> loadInterstitialAdUseCaseProvider;

  private final Provider<ShowInterstitialAdUseCase> showInterstitialAdUseCaseProvider;

  private final Provider<GetAdsConfigUseCase> getAdsConfigUseCaseProvider;

  private final Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider;

  public AdsViewModel_Factory(Provider<LoadNativeMediumAdUseCase> loadNativeMediumAdUseCaseProvider,
      Provider<LoadInterstitialAdUseCase> loadInterstitialAdUseCaseProvider,
      Provider<ShowInterstitialAdUseCase> showInterstitialAdUseCaseProvider,
      Provider<GetAdsConfigUseCase> getAdsConfigUseCaseProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider) {
    this.loadNativeMediumAdUseCaseProvider = loadNativeMediumAdUseCaseProvider;
    this.loadInterstitialAdUseCaseProvider = loadInterstitialAdUseCaseProvider;
    this.showInterstitialAdUseCaseProvider = showInterstitialAdUseCaseProvider;
    this.getAdsConfigUseCaseProvider = getAdsConfigUseCaseProvider;
    this.interstitialAdsRepositoryProvider = interstitialAdsRepositoryProvider;
  }

  @Override
  public AdsViewModel get() {
    return newInstance(loadNativeMediumAdUseCaseProvider.get(), loadInterstitialAdUseCaseProvider.get(), showInterstitialAdUseCaseProvider.get(), getAdsConfigUseCaseProvider.get(), interstitialAdsRepositoryProvider.get());
  }

  public static AdsViewModel_Factory create(
      Provider<LoadNativeMediumAdUseCase> loadNativeMediumAdUseCaseProvider,
      Provider<LoadInterstitialAdUseCase> loadInterstitialAdUseCaseProvider,
      Provider<ShowInterstitialAdUseCase> showInterstitialAdUseCaseProvider,
      Provider<GetAdsConfigUseCase> getAdsConfigUseCaseProvider,
      Provider<InterstitialAdsRepository> interstitialAdsRepositoryProvider) {
    return new AdsViewModel_Factory(loadNativeMediumAdUseCaseProvider, loadInterstitialAdUseCaseProvider, showInterstitialAdUseCaseProvider, getAdsConfigUseCaseProvider, interstitialAdsRepositoryProvider);
  }

  public static AdsViewModel newInstance(LoadNativeMediumAdUseCase loadNativeMediumAdUseCase,
      LoadInterstitialAdUseCase loadInterstitialAdUseCase,
      ShowInterstitialAdUseCase showInterstitialAdUseCase, GetAdsConfigUseCase getAdsConfigUseCase,
      InterstitialAdsRepository interstitialAdsRepository) {
    return new AdsViewModel(loadNativeMediumAdUseCase, loadInterstitialAdUseCase, showInterstitialAdUseCase, getAdsConfigUseCase, interstitialAdsRepository);
  }
}
