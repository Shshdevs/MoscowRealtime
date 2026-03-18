package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import org.jetbrains.compose.resources.StringResource

data class GroupPageUiModel(
    val group: GroupDetailed,
    val allowCRUD: Boolean,
    val editGroupModeOn: Boolean = false,
    val addUserToGroupDialogOpen: Boolean = false,
    val removeUserFromGroupDialogOpen: Boolean = false,
    val deleteGroupDialogOpen: Boolean = false,
    val pinQuestDialogOpen: Boolean = false,
    val searchOrgParticipantsQuery: String = "",
    val deleteUserAlertVisible: User? = null,
    val searchedOrgParticipants: UsersListState = UsersListState.Loading,
    val searchQuestsQuery: String = "",
    val searchedQuests: QuestsListState = QuestsListState.Loading
)