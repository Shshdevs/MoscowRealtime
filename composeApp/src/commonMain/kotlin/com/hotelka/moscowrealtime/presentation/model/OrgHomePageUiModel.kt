package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import org.jetbrains.compose.resources.StringResource

data class OrgHomePageUiModel(
    val currUserIsHost: Boolean,
    val actionInviteClientVisible: Boolean = false,
    val actionInviteOrganizerVisible: Boolean = false,
    val showQuitOrgDialog: Boolean = false,
    val showChangeNameDialog: Boolean = false,
    val searchUsersQuery: String = "",
    val searchedUsers: UsersListState = UsersListState.Loading,
    val message: StringResource? = null,
    val showMessage: Boolean = false
)