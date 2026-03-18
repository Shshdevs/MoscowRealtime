package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class GetUserQuestsUseCase(
    private val questRepository: QuestRepository
){
    suspend operator fun invoke(userId: String): Result<List<Quest>>{
        return questRepository.getUserQuests(userId)
    }
}