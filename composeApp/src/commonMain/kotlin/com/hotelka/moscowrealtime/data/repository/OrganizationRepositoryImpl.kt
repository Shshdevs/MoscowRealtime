package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.OrganizationDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrganizationRepositoryImpl(
    private val organizationDataSource: OrganizationDataSource
) : OrganizationRepository {
    override suspend fun createOrganization(organization: Organization): Result<String> {
        return organizationDataSource.createOrganization(organization.toDto())
    }

    override suspend fun checkExistingOrganizations(name: String): Result<List<String>> {
        return organizationDataSource.checkExistingOrganizations(name)
    }

    override suspend fun addUserToOrganization(
        userId: String,
        organizationId: String
    ): Result<Unit> {
        return organizationDataSource.addUserToOrganization(userId, organizationId)
    }

    override suspend fun removeUserFromOrganization(
        userId: String,
        organizationId: String
    ): Result<Unit> {
        return organizationDataSource.removeUserFromOrganization(userId, organizationId)
    }

    override suspend fun updateOrganizationName(
        organizationId: String,
        name: String
    ): Result<Unit> {
        return organizationDataSource.updateOrganizationName(organizationId, name)
    }

    override suspend fun addUserHostToOrganization(
        userHostId: String,
        organizationId: String
    ): Result<Unit> {
        return organizationDataSource.addUserHostToOrganization(userHostId, organizationId)
    }

    override suspend fun removeUserHostFromOrganization(
        userHostId: String,
        organizationId: String
    ): Result<Unit> {
        return organizationDataSource.removeUserHostFromOrganization(userHostId, organizationId)
    }

    override suspend fun observeOrganization(organizationId: String): Flow<Result<Organization>> {
        return organizationDataSource.observeOrganization(organizationId)
            .map { result -> result.map { it.toDomain() } }
    }

    override suspend fun getOrganizations(organizationIds: List<String>): Result<List<Organization>> {
        return organizationDataSource.getOrganizations(organizationIds)
            .map { it -> it.map { organization -> organization.toDomain() } }
    }

    override suspend fun getOrganization(organizationId: String): Result<Organization> {
        return organizationDataSource.getOrganization(organizationId).map { it.toDomain() }
    }

    override suspend fun deleteOrganization(organizationId: String): Result<Unit> {
        return organizationDataSource.deleteOrganization(organizationId)
    }

    override suspend fun changeOrganizationName(
        organizationId: String,
        name: String
    ): Result<Unit> {
        return organizationDataSource.changeOrganizationName(organizationId, name)
    }
}