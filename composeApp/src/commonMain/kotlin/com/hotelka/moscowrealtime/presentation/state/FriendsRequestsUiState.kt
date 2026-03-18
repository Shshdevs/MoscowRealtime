package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.detailed.FriendRequestDetailed

sealed class FriendsRequestsUiState {
    object Loading: FriendsRequestsUiState()
    data class Success(val friendRequests: List<FriendRequestDetailed>): FriendsRequestsUiState()
    data class Error(val message: String): FriendsRequestsUiState()
}