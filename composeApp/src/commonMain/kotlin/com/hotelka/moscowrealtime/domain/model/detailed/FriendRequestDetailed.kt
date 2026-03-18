package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.User

data class FriendRequestDetailed (
    val friendRequest: FriendRequest,
    val userFrom: User
)