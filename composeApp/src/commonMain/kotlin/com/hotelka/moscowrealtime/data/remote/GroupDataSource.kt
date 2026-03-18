package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.GroupDto
import kotlinx.coroutines.flow.Flow

interface GroupDataSource {
    suspend fun createGroup(group: GroupDto): Result<Unit>
    suspend fun observeOrganizationGroups(organizationId: String): Flow<Result<List<GroupDto>>>
    suspend fun getOrganizationGroups(organizationId: String): Result<List<GroupDto>>
    suspend fun removeUserFromGroup(userId: String, groupId: String): Result<Unit>
    suspend fun deleteUserFromOrganizationGroups(userId: String, organizationId: String): Result<Unit>
    suspend fun addUserToGroup(userId: String, groupId: String): Result<Unit>
    suspend fun pinQuest(groupId: String, questId: String): Result<Unit>
    suspend fun deleteGroup(groupId: String): Result<Unit>

}