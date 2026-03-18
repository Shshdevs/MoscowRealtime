package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository

class SendFriendRequestUseCase(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(userTo: String): Result<Unit>{
        val currUserId = authRepository.getCurrentUserId()?: return Result.failure(Exception("User not authenticated"))
        val friendRequest = FriendRequest(
            userTo = userTo,
            userFrom = currUserId,
        )
        return friendRequestRepository.sendFriendRequest(friendRequest)
    }
}