package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.FriendRequestDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FriendRequestRepositoryImpl(
    private val friendRequestDataSource: FriendRequestDataSource
) : FriendRequestRepository {
    override suspend fun isFriendRequestExists(
        userFrom: String,
        userTo: String
    ): Result<Boolean> {
        return friendRequestDataSource.isFriendRequestsExists(userFrom, userTo)
    }


    override suspend fun observeFriendRequests(userId: String): Flow<Result<List<FriendRequest>>> {
        return friendRequestDataSource.observeFriendRequests(userId)
            .map { result -> result.map { list -> list.map { it.toDomain() } } }
    }

    override suspend fun updateFriendRequest(friendRequest: FriendRequest): Result<Unit> {
        return friendRequestDataSource.updateFriendRequest(friendRequest.toDto())
    }

    override suspend fun deleteFriendRequest(
        userFrom: String,
        userTo: String
    ): Result<Unit> {
        return  friendRequestDataSource.deleteFriendRequest(userFrom, userTo)
    }

    override suspend fun sendFriendRequest(friendRequest: FriendRequest): Result<Unit> {
       return friendRequestDataSource.sendFriendRequest(friendRequest.toDto())
    }

    override suspend fun removeFriendInvitationsForUser(userId: String): Result<Unit> {
        return friendRequestDataSource.removeFriendInvitationsForUser(userId)
    }

    override suspend fun observeFriendRequest(
        currUserId: String,
        userId: String
    ): Flow<Result<FriendRequest?>> {
        return friendRequestDataSource.observeFriendRequest(currUserId, userId).map { result -> result.map { it?.toDomain() } }
    }
}