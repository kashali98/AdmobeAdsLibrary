package com.lib.ads.data.config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class FirebaseAdsConfigRepository_Factory implements Factory<FirebaseAdsConfigRepository> {
  private final Provider<FirebaseRemoteConfig> remoteConfigProvider;

  public FirebaseAdsConfigRepository_Factory(Provider<FirebaseRemoteConfig> remoteConfigProvider) {
    this.remoteConfigProvider = remoteConfigProvider;
  }

  @Override
  public FirebaseAdsConfigRepository get() {
    return newInstance(remoteConfigProvider.get());
  }

  public static FirebaseAdsConfigRepository_Factory create(
      Provider<FirebaseRemoteConfig> remoteConfigProvider) {
    return new FirebaseAdsConfigRepository_Factory(remoteConfigProvider);
  }

  public static FirebaseAdsConfigRepository newInstance(FirebaseRemoteConfig remoteConfig) {
    return new FirebaseAdsConfigRepository(remoteConfig);
  }
}
