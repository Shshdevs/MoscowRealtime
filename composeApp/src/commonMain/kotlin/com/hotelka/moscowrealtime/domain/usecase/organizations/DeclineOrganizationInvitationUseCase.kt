package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository

class DeclineOrganizationInvitationUseCase(
    private val organizationInvitationRepository: OrganizationInvitationRepository
) {
    suspend operator fun invoke(organizationInvitation: OrganizationInvitation): Result<Unit> {
        return organizationInvitationRepository.deleteInvitation(organizationInvitation.id)
    }
}