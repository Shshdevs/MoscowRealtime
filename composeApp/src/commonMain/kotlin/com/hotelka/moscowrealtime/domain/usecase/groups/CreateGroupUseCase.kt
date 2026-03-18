package com.hotelka.moscowrealtime.domain.usecase.groups

import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.GroupRepository

class CreateGroupUseCase(
    private val authRepository: AuthRepository,
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(organizationId: String, name: String): Result<Unit> {
        val hostUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(Exception("User not authenticated"))
        val group = Group(
            id = "",
            organizationId = organizationId,
            groupName = name,
            organizerId = hostUserId,
            users = listOf(),
            pinnedQuestId = null
        )
        return groupRepository.createGroup(group)
    }
}