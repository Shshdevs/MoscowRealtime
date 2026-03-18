package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState

data class FindFriendsPageUiModel(
    val searchedUsers: UsersListState = UsersListState.Loading,
    val searchFinalQuery: String = "",
)