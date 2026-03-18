package com.hotelka.moscowrealtime.domain.usecase.groups

import com.hotelka.moscowrealtime.domain.repository.GroupRepository

class RemoveUserFromGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(userId: String, groupId: String): Result<Unit> {
        return groupRepository.removeUserFromGroup(userId, groupId)
    }
}