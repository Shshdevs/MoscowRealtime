package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveFriendRequestsUseCase(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(): Flow<Result<List<FriendRequest>>> {
        val currUserId = authRepository.getCurrentUserId() ?: return flow {
            emit(
                Result.failure(UserNotAuthenticatedException())
            )
        }
        return friendRequestRepository.observeFriendRequests(currUserId)
    }
}

