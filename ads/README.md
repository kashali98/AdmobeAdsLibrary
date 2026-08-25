# Next-Gen AdMob Ads Module

A reusable, Clean Architecture + MVVM implementation of **Google AdMob Next-Gen
Mobile Ads** (`com.google.android.libraries.ads.mobile.sdk`). Supports **Native
Medium** and **Interstitial** ads, driven entirely by **Firebase Remote Config**.

## Modules

| Module      | Responsibility |
|-------------|----------------|
| `:adsImp`   | Reusable, **SDK-agnostic** contracts: domain models (`AdFormat`, `AdsConfig`, `AdLoadState`, `AdEvent`, `AdError`), the `AdsConfigRepository` contract, `GetAdsConfigUseCase`, `NetworkMonitor`, and the `BaseAdView` UI shell. Depends on **no** ad SDK. |
| `:ads`      | **All** Next-Gen SDK code: data layer (repositories + `AdsInitializer` + Firebase config), domain use cases, and the MVVM presentation layer (`AdsViewModel`, `NativeMediumAdView`). Wires everything with Hilt. |
| `:app`      | Sample consumer. Two **product flavors** isolate the SDKs (see below). |

```
:app (nextgen flavor) ─▶ :ads ─▶ :adsImp
:app (legacy  flavor) ─▶ :libraryads
```

### Why flavors?

The GMA Next-Gen SDK (`ads-mobile-sdk`) **bundles the entire classic
`com.google.android.gms.ads.*` package**, so it cannot be on the same classpath as
the legacy `play-services-ads` / `play-services-ads-lite`. The sample app therefore
uses two flavors on the `adsSdk` dimension, each linking exactly one SDK via its own
source set:

| Flavor | Links | Source set | Launcher | Firebase |
|--------|-------|-----------|----------|----------|
| `legacy`  | `:libraryads` (classic GMA) | `src/legacy` | `MainActivity` | not used |
| `nextgen` | `:ads` (Next-Gen)          | `src/nextgen` | `NextGenAdsActivity` | required |

Shared resources/manifest live in `src/main`. Pick the variant in Android Studio's
*Build Variants* panel, or build directly:

```bash
./gradlew :app:assembleLegacyDebug     # no google-services.json needed
./gradlew :app:assembleNextgenDebug    # needs app/google-services.json
```

Business logic never references an AdMob type — screens talk to `AdsViewModel` and
drop in `NativeMediumAdView`.

## Architecture (Clean Architecture + MVVM)

```
presentation/   AdsViewModel (MVVM), NativeMediumAdView (+ bind() helper)
      │
domain/usecase/ LoadNativeMediumAdUseCase, LoadInterstitialAdUseCase, ShowInterstitialAdUseCase
      │         (+ :adsImp GetAdsConfigUseCase)
data/           NativeAdsRepository, InterstitialAdsRepository (Next-Gen SDK),
                FirebaseAdsConfigRepository (Remote Config), AdsInitializer
      │
di/             Hilt modules binding contracts → implementations
```

## Usage (any screen)

```kotlin
@AndroidEntryPoint
class MyActivity : AppCompatActivity() {

    private val adsViewModel: AdsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Native Medium — one line: loads, renders, cleans up with the lifecycle.
        binding.nativeMediumAd.bind(adsViewModel, this)

        // Interstitial — preload, then show behind an action.
        adsViewModel.loadInterstitial()
        binding.next.setOnClickListener {
            adsViewModel.showInterstitial(this, AdPlacement.HOME) {
                // Always runs once: after dismissal, or immediately if not shown.
                startActivity(Intent(this, NextScreen::class.java))
            }
        }
    }
}
```

Add the view in XML:

```xml
<com.lib.ads.presentation.view.NativeMediumAdView
    android:id="@+id/nativeMediumAd"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## Firebase Remote Config parameters

Create these parameters in the Firebase console (all optional — the module falls
back to Google's official **test** IDs until they exist):

| Key | Type | Default |
|-----|------|---------|
| `ads_master_enabled` | Boolean | `true` |
| `ads_application_id` | String | `ca-app-pub-3940256099942544~3347511713` |
| `ads_native_medium_enabled` | Boolean | `true` |
| `ads_native_medium_unit_id` | String | `ca-app-pub-3940256099942544/2247696110` |
| `ads_interstitial_enabled` | Boolean | `true` |
| `ads_interstitial_unit_id` | String | `ca-app-pub-3940256099942544/1033173712` |
| `ads_interstitial_interval_seconds` | Number | `15` |
| `ads_test_mode` | Boolean | `true` |

## One-time setup you must complete

1. **Add `google-services.json`** to `app/` (from your Firebase project). Only the
   `nextgen` flavor needs it — the `legacy` flavor's Google Services task is disabled
   so it keeps building without Firebase. The `nextgen` variant will not build until
   the file is present.
2. The `nextgen` flavor uses **minSdk 24** (required by the Next-Gen SDK); `legacy`
   stays at 21.
3. Replace the test IDs above with your real AdMob IDs via Remote Config.

## Notes

- The Next-Gen SDK is initialized once, on a background thread (`AdsInitializer`),
  using the app id from Remote Config.
- The legacy `:libraryads` (GMA classic) module is untouched and continues to work
  alongside this module during migration.
