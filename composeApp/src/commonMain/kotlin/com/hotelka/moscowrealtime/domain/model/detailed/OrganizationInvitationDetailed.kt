package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation

data class OrganizationInvitationDetailed (
    val invitation: OrganizationInvitation,
    val organization: Organization
)