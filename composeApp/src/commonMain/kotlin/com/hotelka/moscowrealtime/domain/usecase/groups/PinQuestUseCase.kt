package com.hotelka.moscowrealtime.domain.usecase.groups

import com.hotelka.moscowrealtime.domain.repository.GroupRepository

class PinQuestUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: String, questId: String): Result<Unit> {
        return groupRepository.pinQuest(groupId, questId)
    }
}