package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository

class DeclineFriendRequestUseCase(
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(friendRequest: FriendRequest): Result<Unit>{
        return friendRequestRepository.deleteFriendRequest(friendRequest.userFrom, friendRequest.userTo)
    }
}