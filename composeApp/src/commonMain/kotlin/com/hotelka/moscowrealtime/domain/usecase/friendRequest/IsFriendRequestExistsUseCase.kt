package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository

class IsFriendRequestExistsUseCase(
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(userFrom: String, userTo: String): Result<Boolean> {
        return friendRequestRepository.isFriendRequestExists(userFrom, userTo)
    }
}