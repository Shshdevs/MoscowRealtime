package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class DeleteFriendUseCase(
    val authRepository: AuthRepository,
    val userRepository: UserRepository,
    val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        val currUserId = authRepository.getCurrentUserId()
        return if (currUserId == null) Result.failure(UserNotAuthenticatedException())
        else {
            userRepository.removeFriend(friendId, currUserId)
            userRepository.removeFriend(currUserId, friendId)
            friendRequestRepository.deleteFriendRequest(currUserId, friendId)
            friendRequestRepository.deleteFriendRequest(friendId, currUserId)
        }
    }
}