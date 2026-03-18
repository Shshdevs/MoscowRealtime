package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.repository.GroupRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetCurrentUserOrganizationIdUseCase

class QuitOrganizationUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val userRepository: UserRepository,
    private val getCurrentUserOrganizationIdUseCase: GetCurrentUserOrganizationIdUseCase,
    private val organizationRepository: OrganizationRepository,
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching{
        val currUserId = getCurrentUserIdUseCase()?:throw UserNotAuthenticatedException()
        val orgId = getCurrentUserOrganizationIdUseCase()?:throw Exception("User not in organization")
        userRepository.getUser(currUserId).onSuccess { userRepository.updateUser(it.copy(organizationId = null)) }
        organizationRepository.removeUserFromOrganization(currUserId, orgId)
        organizationRepository.removeUserHostFromOrganization(currUserId, orgId)
        groupRepository.deleteUserFromOrganizationGroups(currUserId, orgId)
    }
}