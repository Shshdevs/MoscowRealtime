package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.OrganizationDto
import kotlinx.coroutines.flow.Flow

interface OrganizationDataSource {
    suspend fun createOrganization(organization: OrganizationDto): Result<String>
    suspend fun checkExistingOrganizations(name: String): Result<List<String>>
    suspend fun addUserToOrganization(userId: String, organizationId: String): Result<Unit>
    suspend fun removeUserFromOrganization(userId: String, organizationId: String): Result<Unit>
    suspend fun updateOrganizationName(organizationId: String, name: String): Result<Unit>
    suspend fun addUserHostToOrganization(userHostId: String, organizationId: String): Result<Unit>
    suspend fun removeUserHostFromOrganization(userHostId: String, organizationId: String): Result<Unit>
    suspend fun observeOrganization(organizationId: String): Flow<Result<OrganizationDto>>
    suspend fun getOrganization(organizationId: String): Result<OrganizationDto>
    suspend fun getOrganizations(organizationIds: List<String>): Result<List<OrganizationDto>>
    suspend fun deleteOrganization(organizationId: String): Result<Unit>
    suspend fun changeOrganizationName(organizationId: String, name: String): Result<Unit>

}