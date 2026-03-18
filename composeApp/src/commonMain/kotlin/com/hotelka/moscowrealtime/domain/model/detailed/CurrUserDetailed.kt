package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.model.User

data class CurrUserDetailed(
    val user: User,
    val email: String,
    val friends: List<User> = emptyList(),
    val scores: List<Score> = emptyList(),
    val discovers: List<DiscoverDetailed> = emptyList(),
    val questProgress: QuestProgressDetailed? = null,
    val organization: Organization? = null,
    val userIsOrganizationHost: Boolean = organization?.usersHosts?.contains(user.id) == true,
    val questsInvolved: List<Quest> = emptyList()
)
