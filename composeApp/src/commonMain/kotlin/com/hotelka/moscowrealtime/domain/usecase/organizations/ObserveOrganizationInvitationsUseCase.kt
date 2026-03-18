package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveOrganizationInvitationsUseCase(
    private val authRepository: AuthRepository,
    private val organizationInvitationRepository: OrganizationInvitationRepository
) {
    suspend operator fun invoke(): Flow<Result<List<OrganizationInvitation>>> {
        val currUserId = authRepository.getCurrentUserId()
        return if (currUserId == null) {
            flow {
                emit(
                    Result.failure(
                        Exception("User not authenticated")
                    )
                )
            }
        } else {
            organizationInvitationRepository.observeOrganizationInvitations(currUserId)
        }
    }
}