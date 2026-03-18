package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(group: Group): Result<Unit>
    suspend fun observeOrganizationGroups(organizationId: String): Flow<Result<List<Group>>>
    suspend fun getOrganizationGroups(organizationId: String): Result<List<Group>>
    suspend fun removeUserFromGroup(userId: String, groupId: String): Result<Unit>
    suspend fun deleteUserFromOrganizationGroups(userId: String, organizationId: String): Result<Unit>
    suspend fun addUserToGroup(userId: String, groupId: String): Result<Unit>
    suspend fun pinQuest(groupId: String, questId: String): Result<Unit>
    suspend fun deleteGroup(groupId: String): Result<Unit>
}