package com.lib.ads.data.nativead;

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
public final class NativeAdsRepositoryImpl_Factory implements Factory<NativeAdsRepositoryImpl> {
  @Override
  public NativeAdsRepositoryImpl get() {
    return newInstance();
  }

  public static NativeAdsRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NativeAdsRepositoryImpl newInstance() {
    return new NativeAdsRepositoryImpl();
  }

  private static final class InstanceHolder {
    static final NativeAdsRepositoryImpl_Factory INSTANCE = new NativeAdsRepositoryImpl_Factory();
  }
}
