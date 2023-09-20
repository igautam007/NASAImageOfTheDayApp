package com.gautamgelani.nasaopenapiimages.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        var isConnected = false
        if (connectivityManager is ConnectivityManager) {

            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            isConnected = networkCapabilities?.let {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false || networkCapabilities?.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ) == true
            } ?: false
        }
        return isConnected
    }
}