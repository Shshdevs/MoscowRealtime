package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.FriendRequest

sealed class FriendsRequestsListState {
    object Loading: FriendsRequestsListState()
    object None: FriendsRequestsListState()
    data class Success(val requests: List<FriendRequest>): FriendsRequestsListState()
    data class Error(val message: String): FriendsRequestsListState()
}