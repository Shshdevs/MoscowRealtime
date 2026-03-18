package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import kotlinx.coroutines.flow.Flow

interface OrganizationInvitationRepository {
    suspend fun createInvitation(invitation: OrganizationInvitation): Result<Unit>
    suspend fun deleteInvitation(invitationId: String): Result<Unit>
    suspend fun observeOrganizationInvitations(userId: String): Flow<Result<List<OrganizationInvitation>>>
}