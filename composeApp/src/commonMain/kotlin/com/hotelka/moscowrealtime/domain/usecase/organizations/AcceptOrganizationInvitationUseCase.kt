package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class AcceptOrganizationInvitationUseCase(
    private val userRepository: UserRepository,
    private val organizationInvitationRepository: OrganizationInvitationRepository,
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationInvitation: OrganizationInvitation): Result<Unit> =
        runCatching {
            if (organizationInvitation.toBeHost) {
                organizationRepository.addUserHostToOrganization(
                    organizationInvitation.userTo,
                    organizationInvitation.organizationId
                )
            } else {
                organizationRepository.addUserToOrganization(
                    organizationInvitation.userTo,
                    organizationInvitation.organizationId
                )
            }
            organizationInvitationRepository.deleteInvitation(organizationInvitation.id)
            userRepository.getUser(organizationInvitation.userTo).fold(
                onSuccess = { user ->
                    return userRepository.updateUser(user.copy(organizationId = organizationInvitation.organizationId))
                },
                onFailure = { e ->
                    return Result.failure(e)
                }
            )
        }
}