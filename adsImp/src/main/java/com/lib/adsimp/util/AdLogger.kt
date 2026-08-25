package com.lib.adsimp.util

import android.util.Log

/** Tiny logging facade for the ads modules; can be muted in release via [enabled]. */
object AdLogger {
    private const val TAG = "AdsModule"

    @JvmStatic
    var enabled: Boolean = true

    fun d(message: String) {
        if (enabled) Log.d(TAG, message)
    }

    fun w(message: String) {
        if (enabled) Log.w(TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        if (enabled) Log.e(TAG, message, throwable)
    }
}
