package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState

data class QuestPageUiModel(
    val questUiModelState: QuestUiModelState = QuestUiModelState.Loading,
    val searchedFriends: UsersListState = UsersListState.Loading,
    val questId: String = "",
    val showAuthorPhoto: Boolean = false,
    val currUserIsAuthor: Boolean = false,
    val showDeleteQuestAlert: Boolean = false,
    val showFriendsCard: Boolean = false,
)