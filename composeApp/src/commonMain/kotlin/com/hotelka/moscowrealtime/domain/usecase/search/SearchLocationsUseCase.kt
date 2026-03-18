package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchLocationsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<Location>>{
        return searchRepository.searchLocations(query)
    }
}