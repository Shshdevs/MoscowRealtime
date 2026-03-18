package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.FriendRequest

sealed class FriendRequestState {
    object None: FriendRequestState()
    object Loading:  FriendRequestState()
    data class Success(val friendRequest: FriendRequest): FriendRequestState()
    data class Error(val message: String):  FriendRequestState()
}