package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.GeoLocation
import kotlinx.coroutines.flow.Flow

interface GeoLocationHandler {

    suspend fun getCurrentLocation(): GeoLocation?

    fun checkPermissions(): Result<Boolean>

    fun locationUpdates(interval: Long = 1_000L): Flow<GeoLocation>

}