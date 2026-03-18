package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class CreateOrganizationUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(name: String): Result<Unit> = runCatching {
        val currUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(Exception("User not authenticated"))
        val organization = Organization(
            name = name,
            usersHosts = listOf(currUserId),
            users = listOf()
        )
        organizationRepository.createOrganization(organization)
            .fold(
                onSuccess = { organizationId ->
                    userRepository.getUser(currUserId).fold(
                        onSuccess = { user ->
                            return userRepository.updateUser(user.copy(organizationId = organizationId))
                        },
                        onFailure = {e ->
                            return Result.failure(e)
                        }
                    )
                },
                onFailure = { e ->
                    return Result.failure(e)
                }
            )
    }
}

