package com.lib.ads.data.mapper

import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.lib.adsimp.domain.model.AdError

/**
 * Maps Next-Gen SDK errors into the SDK-agnostic [AdError] used across the app.
 *
 * `code` is a Kotlin enum ([LoadAdError.ErrorCode] / [FullScreenContentError.ErrorCode]),
 * so we expose its [Enum.ordinal] as the numeric code and keep the symbolic name
 * (via the enum's `toString()`) in the message for readable logs.
 */
fun LoadAdError.toAdError(): AdError = AdError(code = code.ordinal, message = "$code: $message")

fun FullScreenContentError.toAdError(): AdError = AdError(code = code.ordinal, message = "$code: $message")
