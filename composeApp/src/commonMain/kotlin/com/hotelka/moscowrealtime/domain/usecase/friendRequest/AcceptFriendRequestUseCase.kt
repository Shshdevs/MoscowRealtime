package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class AcceptFriendRequestUseCase(
    val userRepository: UserRepository,
    val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(friendRequest: FriendRequest): Result<Unit> {
        return runCatching {
            userRepository.addFriend(friendRequest.userTo, friendRequest.userFrom)
            userRepository.addFriend(friendRequest.userFrom, friendRequest.userTo)
            friendRequestRepository.updateFriendRequest(friendRequest.copy(status = "friends"))
        }
    }
}