pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "AdmobAdsLibrary"
include(":app")
include(":libraryads")
// Next-Gen AdMob: reusable SDK-agnostic contracts (:adsImp) + Next-Gen implementation (:ads)
include(":adsImp")
include(":ads")
