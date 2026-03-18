package com.hotelka.moscowrealtime.presentation.events

sealed class QuestPageEvents {
    object OnNavigateBack : QuestPageEvents()
    object OnReload : QuestPageEvents()
    object OnChangeShowDeleteQuestAlert: QuestPageEvents()
    object OnDeleteQuest: QuestPageEvents()
    object OnChangeShowStartQuestCard:QuestPageEvents()
    data class OnSearchFriends(val query: String): QuestPageEvents()
    data class OnContinueQuest(val questProgressId: String) : QuestPageEvents()
    data class OnStartQuest(val usersInvited: List<String>) : QuestPageEvents()
    data class OnOpenAuthorProfile(val userId: String): QuestPageEvents()
}