package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.FriendRequestState

data class UserProfileUiModel (
    val user: User,
    val scores: List<Score> = emptyList(),
    val discovers: List<DiscoverDetailed> = emptyList(),
    val questsInvolved: List<Quest> = emptyList(),
    val organization: Organization? = null,
    val questProgress: QuestProgressDetailed? = null,
    val friendRequestState: FriendRequestState = FriendRequestState.Loading,
    val isGalleryOpen: Boolean = false,
    val showProfile: Boolean = false,
    val showPhotos: Boolean = false,
    val showQuestsInvolved: Boolean = false,
    val showUnFriendAlert: Boolean = false,
    val openDiscover: DiscoverDetailed? = null
)