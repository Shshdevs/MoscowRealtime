package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Organization
import kotlinx.coroutines.flow.Flow

interface OrganizationRepository {
    suspend fun createOrganization(organization: Organization): Result<String>
    suspend fun checkExistingOrganizations(name: String): Result<List<String>>
    suspend fun addUserToOrganization(userId: String, organizationId: String): Result<Unit>
    suspend fun removeUserFromOrganization(userId: String, organizationId: String): Result<Unit>
    suspend fun updateOrganizationName(organizationId: String, name: String): Result<Unit>
    suspend fun addUserHostToOrganization(userHostId: String, organizationId: String): Result<Unit>
    suspend fun removeUserHostFromOrganization(userHostId: String, organizationId: String): Result<Unit>
    suspend fun observeOrganization(organizationId: String): Flow<Result<Organization>>
    suspend fun getOrganization(organizationId: String): Result<Organization>
    suspend fun getOrganizations(organizationIds: List<String>): Result<List<Organization>>
    suspend fun deleteOrganization(organizationId: String): Result<Unit>
    suspend fun changeOrganizationName(organizationId: String, name: String): Result<Unit>
}

