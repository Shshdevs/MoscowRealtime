package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class CurrentUserProfileEvents {
    object OnCloseAlert : CurrentUserProfileEvents()
    object OnChangeShowChangePasswordAlert : CurrentUserProfileEvents()
    object OnChangeShowDeleteAccountAlert : CurrentUserProfileEvents()
    object OnChangeShowLogOutAlert : CurrentUserProfileEvents()
    object OnShowCreateOrganizationAlert : CurrentUserProfileEvents()
    object OnSubmitChangeOrgAlert: CurrentUserProfileEvents()
    object OnLogOut : CurrentUserProfileEvents()
    object OnDeleteAccount : CurrentUserProfileEvents()
    object OnChangeOpenSettings : CurrentUserProfileEvents()
    object OnChangeOpenFriends : CurrentUserProfileEvents()
    object OnChangeOpenQuestsInvolved : CurrentUserProfileEvents()
    object OnChangeOpenDiscoversGallery : CurrentUserProfileEvents()
    object OnChangeOpenEditProfileInfo : CurrentUserProfileEvents()
    object OnChangeOpenFriendRequests : CurrentUserProfileEvents()
    object OnChangeOpenQuestInvitations : CurrentUserProfileEvents()
    object FindFriends : CurrentUserProfileEvents()
    object NavigateOrganization : CurrentUserProfileEvents()
    object UpdateProfileInfo : CurrentUserProfileEvents()
    object OnPickPhoto : CurrentUserProfileEvents()
    object OnReload : CurrentUserProfileEvents()

    data class OnCreateOrganization(
        val orgName: String
    ) : CurrentUserProfileEvents()

    data class OnChangePassword(
        val email: String
    ) : CurrentUserProfileEvents()

    data class OnChangeOpenDiscover(
        val discover: DiscoverDetailed?
    ) : CurrentUserProfileEvents()

    data class UpdateProfileSettings(
        val profilePrivate: Boolean,
        val photosPrivate: Boolean
    ) : CurrentUserProfileEvents()

    data class AcceptFriendRequest(
        val request: FriendRequest
    ) : CurrentUserProfileEvents()

    data class DeclineFriendRequest(
        val request: FriendRequest
    ) : CurrentUserProfileEvents()

    data class AcceptQuestInvitation(
        val invitation: QuestInvitation
    ) : CurrentUserProfileEvents()

    data class DeclineQuestInvitation(
        val invitation: QuestInvitation
    ) : CurrentUserProfileEvents()

    data class OnShowChangeOrgAlert(
        val organizationInvitation: OrganizationInvitation?
    ) : CurrentUserProfileEvents()

    data class AcceptOrganizationInvitation(
        val invitation: OrganizationInvitation
    ) : CurrentUserProfileEvents()

    data class DeclineOrganizationInvitation(
        val invitation: OrganizationInvitation
    ) : CurrentUserProfileEvents()

    data class NavigateQuest(
        val questId: String
    ) : CurrentUserProfileEvents()

    data class NavigateUser(
        val user: User
    ) : CurrentUserProfileEvents()

    data class UpdateTempUser(
        val tempUser: TempUserInfo?
    ) : CurrentUserProfileEvents()
}