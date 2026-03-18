package com.hotelka.moscowrealtime.domain.usecase.friendRequest

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailFriendRequest
import com.hotelka.moscowrealtime.domain.model.detailed.FriendRequestDetailed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetFriendRequestsDetailsUseCase(
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase
) {
    suspend operator fun invoke(requests: List<FriendRequest>): List<FriendRequestDetailed> =
        coroutineScope {
            val usersIds = requests.map { it.userFrom }.distinct()
            val users = async {
                getUsersByIdsUseCase(usersIds).getOrNull()?.associateBy { it.id } ?: emptyMap()
            }.await()
            requests.mapNotNull { request ->
                detailFriendRequest(
                    request,
                    users[request.userFrom]
                )
            }
        }
}