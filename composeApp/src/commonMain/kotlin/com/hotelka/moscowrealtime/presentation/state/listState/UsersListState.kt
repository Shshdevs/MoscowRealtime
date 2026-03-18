package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.User

sealed class UsersListState {
    object Loading: UsersListState()
    data class Success(val users: List<User>): UsersListState()
    data class Error(val message: String): UsersListState()
}
