package com.hotelka.moscowrealtime.domain.usecase.groups

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressesUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsByIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailGroup
import com.hotelka.moscowrealtime.domain.model.GroupParticipant
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.domain.model.Organizer
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetOrganizationGroupsDetailsUseCase(
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getQuestProgressesUseCase: GetQuestProgressesUseCase,
    private val getQuestsByIdsUseCase: GetQuestsByIdsUseCase
) {

    suspend operator fun invoke(organization: Organization, groups: List<Group>): Pair<List<GroupDetailed>, List<Organizer>> =
        coroutineScope {
            val groupsOrganizers = organization.usersHosts
            val participantsIds = groups.flatMap { it.users }.distinct()
            val questsIds = groups.mapNotNull { it.pinnedQuestId }.distinct()

            val organizersDeferred = async {
                val organizers = getUsersByIdsUseCase(groupsOrganizers).getOrThrow()
                organizers.associateBy { it.id }
            }
            val participantsDeferred = async {
                val participants = getUsersByIdsUseCase(participantsIds).getOrThrow()
                participants.associateBy { it.id }
            }
            val questsDeferred = async {
                val quests = getQuestsByIdsUseCase(questsIds).getOrThrow()
                quests.associateBy { it.id }
            }

            val participantsQuestsProgressesDeferred = async {
                val questProgresses =
                    getQuestProgressesUseCase(participantsIds, questsIds).getOrDefault(emptyList())
                questProgresses.flatMap { questProgress ->
                    questProgress.users
                        .filter { userId -> userId in participantsIds }
                        .map { userId -> (userId to questProgress.questId) to questProgress }
                }.toMap()
            }

            val organizers = organizersDeferred.await()
            val quests = questsDeferred.await()
            val participants = participantsDeferred.await()
            val participantsQuestsProgresses = participantsQuestsProgressesDeferred.await()

            val groupsUi = groups.mapNotNull { group ->
                val groupParticipants = group.users.mapNotNull { userId ->
                    participants[userId]?.let { user ->
                        val questProgress =
                            participantsQuestsProgresses[userId to group.pinnedQuestId]
                        val progress =
                            questProgress?.results?.filter { it.success }?.size?.toFloat()
                                ?.div(quests[group.pinnedQuestId]?.locations?.size?.toFloat() ?: 0f)
                        GroupParticipant(
                            user,
                            progress
                        )
                    }
                }
                organizers[group.organizerId]?.let { organizer ->
                    detailGroup(
                        group,
                        quests[group.pinnedQuestId],
                        organizer,
                        groupParticipants
                    )
                }
            }

            val organizersUi = organizers.values.map { organizer ->
                Organizer(
                    organizer = organizer,
                    groups = groupsUi.filter { it.organizer.id == organizer.id }
                )
            }

            Pair(groupsUi, organizersUi)
        }
}