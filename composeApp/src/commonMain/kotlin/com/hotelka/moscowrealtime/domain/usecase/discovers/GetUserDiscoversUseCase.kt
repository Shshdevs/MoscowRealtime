package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository

class GetUserDiscoversUseCase(
    private val discoveriesRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Discover>> {
        return discoveriesRepository.gerUserDiscoveries(userId)
    }
}

