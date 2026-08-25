package com.lib.adsimp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** SDK-agnostic network availability check used to short-circuit ad loads when offline. */
interface NetworkMonitor {
    fun isConnected(): Boolean
}

/**
 * Default [NetworkMonitor] backed by [ConnectivityManager]. Kept as a plain class
 * (no DI annotations) so :adsImp stays free of any DI framework; the :ads Hilt module
 * provides it with the application context.
 */
class AndroidNetworkMonitor(
    private val context: Context,
) : NetworkMonitor {

    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
            caps.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
    }
}
