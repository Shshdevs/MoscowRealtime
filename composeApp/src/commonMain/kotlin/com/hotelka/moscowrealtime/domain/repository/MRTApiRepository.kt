package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.domain.model.api.MRTApiResult

interface MRTApiRepository {
    suspend fun analyzeImage(imgPath: String, userId: String): MRTApiResult<Discover>
    suspend fun getClosestLocation(lat: Double, lon: Double): MRTApiResult<ClosestLocationResult>
}