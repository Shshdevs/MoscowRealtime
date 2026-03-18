package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository

class GetDiscoverUseCase(
    private val discoveriesRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(discoverId: String): Result<Discover> {
        return discoveriesRepository.getDiscover(discoverId)
    }
}