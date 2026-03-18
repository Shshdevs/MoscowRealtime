package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.OrganizationInvitationDto
import kotlinx.coroutines.flow.Flow

interface OrganizationInvitationDataSource {
    suspend fun createInvitation(invitation: OrganizationInvitationDto): Result<Unit>
    suspend fun deleteInvitation(invitationId: String): Result<Unit>
    suspend fun observeOrganizationInvitations(userId: String): Flow<Result<List<OrganizationInvitationDto>>>
}