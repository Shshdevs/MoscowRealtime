package com.hotelka.moscowrealtime.presentation.events

sealed class GroupPageEvents {
    data class OnRemoveUserFromGroup(val userId: String, val groupId: String): GroupPageEvents()
    data class OnAddUserToGroup(val userId: String, val groupId: String): GroupPageEvents()
    data class OnPinGroupQuest(val questId: String, val groupId: String): GroupPageEvents()
    data class OnSearchClients(val query: String) : GroupPageEvents()
    data class OnNavigateQuestPage(val questId: String): GroupPageEvents()
}