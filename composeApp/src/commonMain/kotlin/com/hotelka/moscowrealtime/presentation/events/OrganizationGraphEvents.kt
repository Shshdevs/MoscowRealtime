package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.domain.model.Organizer

sealed class OrganizationGraphEvents

sealed class OrganizationNavEvents : OrganizationGraphEvents() {
    object OnNavigateBack : OrganizationNavEvents()
    object NavigateMyGroups : OrganizationNavEvents()
    data class OnNavigateGroup(val group: GroupDetailed) : OrganizationNavEvents()
    data class OnNavigateOrganizerPage(val organizer: Organizer) : OrganizationNavEvents()
    data class OnNavigateQuestPage(val questId: String) : OrganizationNavEvents()
}

sealed class OrganizationHomePageEvents : OrganizationGraphEvents() {

    object OnReload : OrganizationHomePageEvents()
    object OnChangeShowInviteOrganizer : OrganizationHomePageEvents()
    object OnChangeShowInviteClient : OrganizationHomePageEvents()
    object OnChangeShowOrgNameDialog : OrganizationHomePageEvents()
    object OnChangeShowQuitOrgDialog : OrganizationHomePageEvents()
    object OnQuitOrg : OrganizationHomePageEvents()
    data class OnInvite(val userId: String, val toBeHost: Boolean) : OrganizationHomePageEvents()
    data class OnChangeOrgName(val name: String) : OrganizationHomePageEvents()
    data class OnSearchUsers(val query: String) : OrganizationHomePageEvents()
}

sealed class OrganizerPageEvents : OrganizationGraphEvents() {
    object OnShowCreateGroupDialogError : OrganizerPageEvents()
    object OnChangeShowCreateGroupDialog : OrganizerPageEvents()
    data class OnAddGroup(val groupName: String) : OrganizerPageEvents()
}

sealed class GroupEvents : OrganizationGraphEvents() {
    object OnChangeEditGroup : GroupEvents()
    object OnChangeShowAddUserMenu : GroupEvents()
    object OnChangeShowRemoveUserDialog : GroupEvents()
    object OnChangeOpenPinQuestMenu : GroupEvents()
    object OnChangeShowDeleteGroupDialog : GroupEvents()
    object OnDeleteGroup: GroupEvents()
    data class OnAddUserToGroup(val userId: String) : GroupEvents()
    data class OnRemoveUserFromGroup(val userId: String) : GroupEvents()
    data class OnRemoveUserFromGroupAlert(val user: User?): GroupEvents()
    data class OnPinQuest(val questId: String) : GroupEvents()
    data class OnSearchQuests(val query: String) : GroupEvents()
    data class OnSearchOrgParticipants(val query: String) : GroupEvents()
}