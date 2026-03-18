package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversWithIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.locations.GetLocationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailDiscover
import com.hotelka.moscowrealtime.domain.mappers.detailQuest
import com.hotelka.moscowrealtime.domain.model.detailed.QuestDetailed
import com.hotelka.moscowrealtime.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetQuestDetailsUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getQuestUseCase: GetQuestUseCase,
    private val getQuestProgressUseCase: GetQuestProgressUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getDiscoversWithIdsUseCase: GetDiscoversWithIdsUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getUserUseCase: GetUserUseCase
) {

    suspend operator fun invoke(questId: String): QuestDetailed? {
        val currUserId = getCurrentUserIdUseCase()
        if (currUserId == null) return null
        return coroutineScope {
            val questDeferred = async { getQuestUseCase(questId).getOrElse { e -> throw e } }
            val questProgressDeferred =
                async { getQuestProgressUseCase(currUserId, questId).getOrNull() }

            val quest = questDeferred.await()
            val questProgress = questProgressDeferred.await()

            val participantsIds = quest.participantIds
            val questLocationsIds = quest.locations
            val questProgressDiscoversIds = questProgress?.results?.map { it.result }

            val userAuthorDeferred = async {
                quest.authorId?.let { getUserUseCase(it).getOrNull() }
            }
            val participantsDeferred = async {
                participantsIds.let { getUsersByIdsUseCase(it).getOrDefault(emptyList()) }
            }
            val questLocationsDeferred = async {
                getLocationsUseCase(questLocationsIds).getOrDefault(emptyList())
            }

            val questProgressDiscoversDeferred = async {
                questProgressDiscoversIds?.let { getDiscoversWithIdsUseCase(it).getOrDefault(emptyList()) }
            }

            val questProgressDiscovers = questProgressDiscoversDeferred.await()

            val questProgressDiscoversLocationsIds =
                questProgressDiscovers?.map { it.detections[0].locationId }?.distinct()
                    ?: emptyList()

            val questProgressDiscoversLocationsDeferred = async {
                val locations =
                    getLocationsUseCase(questProgressDiscoversLocationsIds).getOrDefault(emptyList())
                locations.associateBy { it.id }
            }

            val userAuthor = userAuthorDeferred.await()
            val participants = participantsDeferred.await()
            val questLocations = questLocationsDeferred.await()
            val questProgressDiscoversLocations = questProgressDiscoversLocationsDeferred.await()

            detailQuest(
                quest,
                questProgress,
                questLocations,
                participants,
                questProgressDiscovers?.map { discover ->
                    detailDiscover(
                        discover,
                        null,
                        questProgressDiscoversLocations[discover.detections[0].locationId]
                    )
                } ?: emptyList(),
                userAuthor
            )
        }
    }
}