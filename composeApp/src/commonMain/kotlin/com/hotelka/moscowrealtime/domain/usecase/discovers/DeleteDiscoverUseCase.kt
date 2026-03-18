package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository

class DeleteDiscoverUseCase(
    private val discoveriesRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(discoverId: String): Result<Unit> {
        return discoveriesRepository.deleteUserDiscover(discoverId)
    }
}