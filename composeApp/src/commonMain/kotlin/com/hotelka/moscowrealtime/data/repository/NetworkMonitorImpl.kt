package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.NetworkMonitor
import kotlinx.coroutines.flow.StateFlow

expect class NetworkMonitorImpl(): NetworkMonitor {
    override fun startMonitoring()
    override fun stopMonitoring()
    override val isOnline: StateFlow<Boolean>
}