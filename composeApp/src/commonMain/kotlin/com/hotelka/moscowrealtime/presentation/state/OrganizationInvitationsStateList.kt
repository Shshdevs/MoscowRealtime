package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.detailed.OrganizationInvitationDetailed

sealed class OrganizationInvitationsStateList {
    object None: OrganizationInvitationsStateList()
    object Loading: OrganizationInvitationsStateList()
    data class Success(val invitationsUiModels:List<OrganizationInvitationDetailed>):OrganizationInvitationsStateList()
    data class Error(val error: String):OrganizationInvitationsStateList()
}