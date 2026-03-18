package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.detailed.OrganizationInvitationDetailed

fun detailOrganizationInvitation(
    invitation: OrganizationInvitation,
    organization: Organization?
): OrganizationInvitationDetailed? = organization?.let {
    OrganizationInvitationDetailed(
        invitation = invitation,
        organization = organization
    )
}