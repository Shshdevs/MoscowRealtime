package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.User

data class QuestInvitationDetailed(
    val questInvitation: QuestInvitation,
    val quest: Quest,
    val userFrom: User
)