package com.hotelka.moscowrealtime.domain.usecase.locations

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.LocationsRepository

class GetLocationsUseCase(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(locationIds: List<String>): Result<List<Location>> {
        return locationsRepository.getLocationsByIds(locationIds)
    }
}

