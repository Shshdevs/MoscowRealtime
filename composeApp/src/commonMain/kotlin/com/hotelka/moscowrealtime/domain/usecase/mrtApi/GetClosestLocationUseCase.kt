package com.hotelka.moscowrealtime.domain.usecase.mrtApi

import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.domain.model.api.MRTApiResult
import com.hotelka.moscowrealtime.domain.repository.MRTApiRepository

class GetClosestLocationUseCase(
    private val repository: MRTApiRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): MRTApiResult<ClosestLocationResult> {
        return repository.getClosestLocation(lat, lon)
    }
}