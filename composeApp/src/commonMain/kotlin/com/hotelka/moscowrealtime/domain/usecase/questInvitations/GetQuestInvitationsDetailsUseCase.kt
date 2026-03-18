package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsByIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailQuestInvitation
import com.hotelka.moscowrealtime.domain.model.detailed.QuestInvitationDetailed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetQuestInvitationsDetailsUseCase(
    private val getQuestsByIdsUseCase: GetQuestsByIdsUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase
) {
    suspend operator fun invoke(invitations: List<QuestInvitation>): List<QuestInvitationDetailed> =
        coroutineScope {
            val usersIds = invitations.map { it.userFrom }.distinct()
            val questsIds = invitations.map { it.questId }.distinct()

            val questsDeferred =
                async { getQuestsByIdsUseCase(questsIds).getOrThrow().associateBy { it.id } }
            val usersDeferred =
                async { getUsersByIdsUseCase(usersIds).getOrThrow().associateBy { it.id } }

            val quests = questsDeferred.await()
            val users = usersDeferred.await()

            invitations.mapNotNull { invitation ->
                detailQuestInvitation(
                    invitation,
                    quests[invitation.questId],
                    users[invitation.userFrom]
                )
            }
        }
}