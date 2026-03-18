package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.FriendRequestDetailed

fun detailFriendRequest(
    request: FriendRequest,
    userFrom: User?
): FriendRequestDetailed? =
    userFrom?.let {
        FriendRequestDetailed(
            friendRequest = request,
            userFrom = userFrom
        )
    }