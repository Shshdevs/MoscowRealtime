package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.DiscoverDto
import com.hotelka.moscowrealtime.data.dto.api.ClosestLocationResultDto
import com.hotelka.moscowrealtime.data.dto.api.MRTApiDto

interface MRTApiDataSource {
    suspend fun analyzeImage(imgPath: String, userId: String): MRTApiDto<DiscoverDto>
    suspend fun getClosestLocation(lat: Double, lon: Double): MRTApiDto<ClosestLocationResultDto>
}