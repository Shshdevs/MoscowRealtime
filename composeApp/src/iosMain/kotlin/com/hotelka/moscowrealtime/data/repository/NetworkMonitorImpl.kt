package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class NetworkMonitorImpl: NetworkMonitor {
    private val _isOnline = MutableStateFlow(true)
    actual override val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()


    actual override fun startMonitoring() {

    }

    actual override fun stopMonitoring() {

    }
}