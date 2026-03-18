package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class UserProfileEvents {
    object OnReload: UserProfileEvents()
    object OnChangeOpenGalleryDiscovers : UserProfileEvents()
    object CallUnFriendAlert : UserProfileEvents()
    object OnChangeOpenQuestsInvolved : UserProfileEvents()
    data class OnSendFriendRequest(val userId: String) : UserProfileEvents()
    data class OnNavigateQuestPage(val questId: String) : UserProfileEvents()

    data class OnChangeOpenDiscover(val discover: DiscoverDetailed?) : UserProfileEvents()
    data class OnAcceptFriendRequest(val request: FriendRequest) : UserProfileEvents()
    data class OnUnFriend(val user: User) : UserProfileEvents()
}