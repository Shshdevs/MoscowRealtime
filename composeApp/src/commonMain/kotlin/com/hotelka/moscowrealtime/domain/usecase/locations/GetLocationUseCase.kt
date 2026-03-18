package com.hotelka.moscowrealtime.domain.usecase.locations

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.LocationsRepository

class GetLocationUseCase(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(locationId: String): Result<Location> {
        return locationsRepository.getLocation(locationId)
    }
}