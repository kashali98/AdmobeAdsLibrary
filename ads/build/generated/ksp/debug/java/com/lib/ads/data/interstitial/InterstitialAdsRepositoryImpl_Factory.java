package com.lib.ads.data.interstitial;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class InterstitialAdsRepositoryImpl_Factory implements Factory<InterstitialAdsRepositoryImpl> {
  @Override
  public InterstitialAdsRepositoryImpl get() {
    return newInstance();
  }

  public static InterstitialAdsRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static InterstitialAdsRepositoryImpl newInstance() {
    return new InterstitialAdsRepositoryImpl();
  }

  private static final class InstanceHolder {
    static final InterstitialAdsRepositoryImpl_Factory INSTANCE = new InterstitialAdsRepositoryImpl_Factory();
  }
}
