package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository

class InviteUserToOrganizationUseCase(
    private val organizationInvitationRepository: OrganizationInvitationRepository
) {
    suspend operator fun invoke(organizationId: String, toBeHost: Boolean, userTo: String): Result<Unit> {
        val invitation = OrganizationInvitation(
            id = "",
            organizationId = organizationId,
            toBeHost = toBeHost,
            userTo = userTo
        )
        return organizationInvitationRepository.createInvitation(invitation)
    }
}