package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.GroupDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupRepositoryImpl(
    private val groupDataSource: GroupDataSource
): GroupRepository {
    override suspend fun createGroup(group: Group): Result<Unit> {
        return groupDataSource.createGroup(group.toDto())
    }

    override suspend fun observeOrganizationGroups(organizationId: String): Flow<Result<List<Group>>> {
        return groupDataSource.observeOrganizationGroups(organizationId).map { result -> result.map { groups -> groups.map { it.toDomain() } } }
    }

    override suspend fun getOrganizationGroups(organizationId: String): Result<List<Group>> {
        return groupDataSource.getOrganizationGroups(organizationId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun removeUserFromGroup(
        userId: String,
        groupId: String
    ): Result<Unit> {
       return groupDataSource.removeUserFromGroup(userId, groupId)
    }

    override suspend fun deleteUserFromOrganizationGroups(
        userId: String,
        organizationId: String
    ): Result<Unit> {
        return groupDataSource.deleteUserFromOrganizationGroups(userId, organizationId)
    }

    override suspend fun addUserToGroup(
        userId: String,
        groupId: String
    ): Result<Unit> {
        return groupDataSource.addUserToGroup(userId, groupId)
    }

    override suspend fun pinQuest(
        groupId: String,
        questId: String
    ): Result<Unit> {
        return groupDataSource.pinQuest(groupId, questId)
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        return groupDataSource.deleteGroup(groupId)
    }
}