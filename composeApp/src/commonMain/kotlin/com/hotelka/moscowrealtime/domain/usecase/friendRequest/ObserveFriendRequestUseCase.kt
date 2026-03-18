package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveFriendRequestUseCase (
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(userId: String): Flow<Result<FriendRequest?>> {
        val currUserId =
        authRepository.getCurrentUserId()
        return if (currUserId != null){
            friendRequestRepository.observeFriendRequest(currUserId, userId)
        } else{
            flow { emit(Result.failure(UserNotAuthenticatedException())) }
        }
    }
}
