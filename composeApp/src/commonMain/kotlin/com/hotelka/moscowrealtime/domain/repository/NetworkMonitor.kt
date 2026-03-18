package com.hotelka.moscowrealtime.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    fun startMonitoring()
    fun stopMonitoring()
    val isOnline: StateFlow<Boolean>
}