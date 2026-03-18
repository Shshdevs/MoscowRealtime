package com.hotelka.moscowrealtime.domain.usecase.locations

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.LocationsRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class GetLocationsForQuestUseCase(
    private val questRepository: QuestRepository,
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(questId: String): Result<List<Location>> {
        questRepository.getQuest(questId).fold(
            onSuccess = { quest ->
                return locationsRepository.getLocationsByIds(quest.locations)
            },
            onFailure = { e ->
                return Result.failure(e)
            }
        )
    }
}