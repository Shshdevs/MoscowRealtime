package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.mappers.detailOrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.detailed.OrganizationInvitationDetailed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetOrganizationsBriefDetailsUseCase(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(invitations: List<OrganizationInvitation>): List<OrganizationInvitationDetailed> =
        coroutineScope {
            val organizationIds = invitations.map { it.organizationId }
            val organizations = async {
                organizationRepository.getOrganizations(organizationIds).getOrNull()
                    ?.associateBy { it.id }
                    ?: emptyMap()
            }.await()
            invitations.mapNotNull { invitation ->
                detailOrganizationInvitation(
                    invitation,
                    organizations[invitation.organizationId]
                )
            }
        }
}