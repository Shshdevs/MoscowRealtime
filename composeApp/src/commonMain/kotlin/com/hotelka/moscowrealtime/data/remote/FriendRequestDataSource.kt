package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.FriendRequestDto
import kotlinx.coroutines.flow.Flow

interface FriendRequestDataSource {
    suspend fun isFriendRequestsExists(userFrom: String, userTo: String): Result<Boolean>
    suspend fun observeFriendRequests(userId: String): Flow<Result<List<FriendRequestDto>>>
    fun observeFriendRequest(currUserId: String, userId: String): Flow<Result<FriendRequestDto?>>
    suspend fun updateFriendRequest(friendRequest: FriendRequestDto): Result<Unit>
    suspend fun deleteFriendRequest(userFrom: String, userTo: String): Result<Unit>
    suspend fun sendFriendRequest(friendRequest: FriendRequestDto): Result<Unit>
    suspend fun removeFriendInvitationsForUser(userId: String): Result<Unit>

}