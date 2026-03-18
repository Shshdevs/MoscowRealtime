package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.OrganizationInvitationDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrganizationInvitationRepositoryImpl(
    private val organizationInvitationDataSource: OrganizationInvitationDataSource
) : OrganizationInvitationRepository {
    override suspend fun createInvitation(invitation: OrganizationInvitation): Result<Unit> {
        return organizationInvitationDataSource.createInvitation(invitation.toDto())
    }

    override suspend fun deleteInvitation(invitationId: String): Result<Unit> {
        return organizationInvitationDataSource.deleteInvitation(invitationId)
    }

    override suspend fun observeOrganizationInvitations(userId: String): Flow<Result<List<OrganizationInvitation>>> {
        return organizationInvitationDataSource.observeOrganizationInvitations(userId)
            .map { result -> result.map { list -> list.map { it.toDomain() } } }
    }
}