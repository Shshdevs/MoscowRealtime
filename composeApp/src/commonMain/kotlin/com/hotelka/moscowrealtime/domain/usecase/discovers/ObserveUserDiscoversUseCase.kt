package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveUserDiscoversUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val discoveriesRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Discover>>> {
        val userId = getCurrentUserIdUseCase()?:return flow { emit(Result.failure(UserNotAuthenticatedException())) }
        return discoveriesRepository.observeUserDiscoveries(userId)
    }
}