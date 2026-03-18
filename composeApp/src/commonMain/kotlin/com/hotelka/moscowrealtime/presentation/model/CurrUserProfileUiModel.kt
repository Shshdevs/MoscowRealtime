package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.model.detailed.CurrUserDetailed
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.FriendsRequestsUiState
import com.hotelka.moscowrealtime.presentation.state.OrganizationInvitationsStateList
import com.hotelka.moscowrealtime.presentation.state.listState.QuestInvitationsUiState

data class CurrUserProfileUiModel(
    val userDetailed: CurrUserDetailed,
    val friendRequests: FriendsRequestsUiState =
        FriendsRequestsUiState.Loading,
    val questInvitations: QuestInvitationsUiState =
        QuestInvitationsUiState.Loading,
    val organizationInvitations: OrganizationInvitationsStateList =
        OrganizationInvitationsStateList.Loading,
    val tempUser: TempUserInfo? = null,

    val isSettingsOpen: Boolean = false,
    val isEditProfileInfoOpen: Boolean = false,
    val isDiscoversGalleryOpen: Boolean = false,
    val isFriendsOpen: Boolean = false,
    val isFriendRequestsOpen:Boolean = false,
    val isQuestInvitationsOpen:Boolean = false,
    val isQuestsInvolvedOpen: Boolean = false,
    val userIsOrganizationHost: Boolean = false,
    val showChangePasswordAlert:Boolean = false,
    val showLogOutAlert: Boolean = false,
    val showDeleteAccountAlert: Boolean = false,
    val showCreateOrganizationAlert: Boolean = false,
    val restartingQuestAlert: QuestInvitation? = null,
    val showChangeOrgAlert: OrganizationInvitation? = null,
    val openDiscover: DiscoverDetailed? = null,
)