package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.QuestInvitationDetailed

fun detailQuestInvitation(
    invitation: QuestInvitation,
    quest: Quest?,
    userFrom: User?
): QuestInvitationDetailed? = quest?.let {
    userFrom?.let {
        QuestInvitationDetailed(
            questInvitation = invitation,
            quest = quest,
            userFrom = userFrom
        )
    }
}