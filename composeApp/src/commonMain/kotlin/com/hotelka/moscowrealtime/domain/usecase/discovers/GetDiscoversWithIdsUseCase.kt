package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository

class GetDiscoversWithIdsUseCase(
    private val discoveriesRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(discoverIds: List<String>): Result<List<Discover>> {
        return if (discoverIds.isNotEmpty()) discoveriesRepository.getDiscoversByIds(discoverIds) else Result.success(listOf())
    }
}
