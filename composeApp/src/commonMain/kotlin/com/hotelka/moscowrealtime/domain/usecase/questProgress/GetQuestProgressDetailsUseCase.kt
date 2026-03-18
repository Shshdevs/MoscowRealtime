package com.hotelka.moscowrealtime.domain.usecase.questProgress

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversWithIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.locations.GetLocationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailDiscover
import com.hotelka.moscowrealtime.domain.mappers.detailQuestProgress
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetQuestProgressDetailsUseCase(
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getDiscoversWithIdsUseCase: GetDiscoversWithIdsUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getQuestUseCase: GetQuestUseCase
) {
    suspend operator fun invoke(questProgress: QuestProgress): QuestProgressDetailed? {
        return coroutineScope {
            val discoversDeferred = async {
                getDiscoversWithIdsUseCase(questProgress.results.map { it.result }).getOrDefault(
                    emptyList()
                )
            }

            val questDeferred = async {
                getQuestUseCase(questProgress.questId).getOrNull()
            }

            val discovers = discoversDeferred.await()
            val quest = questDeferred.await()
            if (quest == null) return@coroutineScope null

            val usersIds = discovers.map { it.userAuthor }
            val discoverLocationsIds = discovers.map { it.detections[0].locationId }

            val discoversAuthorsDeferred = async {
                val users = getUsersByIdsUseCase(usersIds).getOrDefault(emptyList())
                users.associateBy { it.id }
            }

            val discoverLocationsMapDeferred = async {
                val locations = getLocationsUseCase(discoverLocationsIds).getOrDefault(emptyList())
                locations.associateBy { it.id }
            }

            val locationsDeferred = async {
                getLocationsUseCase(quest.locations).getOrDefault(emptyList())
            }

            val discoversAuthors = discoversAuthorsDeferred.await()
            val discoverLocationsMap = discoverLocationsMapDeferred.await()
            val locations = locationsDeferred.await()

            detailQuestProgress(
                questProgress,
                quest,
                locations,
                discovers.map { discover ->
                    detailDiscover(
                        discover,
                        discoversAuthors[discover.userAuthor],
                        discoverLocationsMap[discover.detections.first().locationId]
                    )
                }
            )
        }
    }
}