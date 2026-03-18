package com.hotelka.moscowrealtime.domain.usecase.organizations

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ObserveOrganizationUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val organizationRepository: OrganizationRepository
) {
    operator fun invoke(): Flow<Result<Organization>> = flow {
        val userId = authRepository.getCurrentUserId()
        if (userId == null) {
            emit(Result.failure(Exception(UserNotAuthenticatedException())))
            return@flow
        }
        userRepository.getUser(userId).fold(
            onSuccess = { user ->
                user.organizationId?.let {
                    emitAll(organizationRepository.observeOrganization(it))
                }
            },
            onFailure = { e ->
                emit(Result.failure(Exception("Error getting user")))
            }
        )
    }
    suspend operator fun invoke(organizationId: String): Flow<Result<Organization>> {
        val result = organizationRepository.observeOrganization(organizationId)
        return result
    }
}