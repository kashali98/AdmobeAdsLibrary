package com.lib.ads.data.initializer;

import android.content.Context;
import com.lib.adsimp.domain.config.AdsConfigRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AdsInitializer_Factory implements Factory<AdsInitializer> {
  private final Provider<Context> contextProvider;

  private final Provider<AdsConfigRepository> configRepositoryProvider;

  public AdsInitializer_Factory(Provider<Context> contextProvider,
      Provider<AdsConfigRepository> configRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.configRepositoryProvider = configRepositoryProvider;
  }

  @Override
  public AdsInitializer get() {
    return newInstance(contextProvider.get(), configRepositoryProvider.get());
  }

  public static AdsInitializer_Factory create(Provider<Context> contextProvider,
      Provider<AdsConfigRepository> configRepositoryProvider) {
    return new AdsInitializer_Factory(contextProvider, configRepositoryProvider);
  }

  public static AdsInitializer newInstance(Context context, AdsConfigRepository configRepository) {
    return new AdsInitializer(context, configRepository);
  }
}
