package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.model.GeoLocation
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import kotlinx.coroutines.flow.Flow

expect class GeoLocationHandlerImpl() : GeoLocationHandler {
    override suspend fun getCurrentLocation(): GeoLocation?

    override fun checkPermissions(): Result<Boolean>

    override fun locationUpdates(interval: Long): Flow<GeoLocation>

}