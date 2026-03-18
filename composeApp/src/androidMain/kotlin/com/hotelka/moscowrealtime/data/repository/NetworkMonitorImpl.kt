package com.hotelka.moscowrealtime.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.hotelka.moscowrealtime.AndroidContextHolder
import com.hotelka.moscowrealtime.domain.repository.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class NetworkMonitorImpl : NetworkMonitor {
    private val _isOnline = MutableStateFlow(true)
    actual override val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val connectivityManager by lazy {
        AndroidContextHolder.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isOnline.value = true
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            _isOnline.value = false
        }

        override fun onUnavailable() {
            _isOnline.value = false
        }

        override fun onLost(network: Network) {
            _isOnline.value = false
        }
    }

    actual override fun startMonitoring() {
        checkInitialConnection()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun checkInitialConnection() {
        connectivityManager.activeNetwork?.let {
            val capabilities = connectivityManager.getNetworkCapabilities(it)
            val hasInternet =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            val isValidated =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false

            _isOnline.value = hasInternet && isValidated
        }
    }

    actual override fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}