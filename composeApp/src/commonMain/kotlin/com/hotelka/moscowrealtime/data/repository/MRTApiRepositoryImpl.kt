package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.MRTApiDataSource
import com.hotelka.moscowrealtime.data.mapper.LocationMapper
import com.hotelka.moscowrealtime.data.mapper.api.toDomain
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.domain.model.api.MRTApiResult
import com.hotelka.moscowrealtime.domain.repository.MRTApiRepository

class MRTApiRepositoryImpl(
    private val apiDataSource: MRTApiDataSource,
    private val locationMapper: LocationMapper
) : MRTApiRepository {
    override suspend fun analyzeImage(
        imgPath: String,
        userId: String
    ): MRTApiResult<Discover> {
        return apiDataSource.analyzeImage(imgPath, userId).toDomain { it.toDomain() }
    }

    override suspend fun getClosestLocation(
        lat: Double,
        lon: Double
    ): MRTApiResult<ClosestLocationResult> {

        return apiDataSource.getClosestLocation(lat, lon).toDomain { it.toDomain(locationMapper) }
    }
}