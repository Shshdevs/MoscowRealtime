package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import kotlinx.coroutines.flow.Flow

interface FriendRequestRepository {
    suspend fun isFriendRequestExists(userFrom: String, userTo: String): Result<Boolean>
    suspend fun observeFriendRequests(userId: String): Flow<Result<List<FriendRequest>>>
    suspend fun updateFriendRequest(friendRequest: FriendRequest): Result<Unit>
    suspend  fun deleteFriendRequest(userFrom: String, userTo: String): Result<Unit>
    suspend fun sendFriendRequest(friendRequest: FriendRequest): Result<Unit>
    suspend fun removeFriendInvitationsForUser(userId: String): Result<Unit>
    suspend fun observeFriendRequest(currUserId: String, userId: String): Flow<Result<FriendRequest?>>
}