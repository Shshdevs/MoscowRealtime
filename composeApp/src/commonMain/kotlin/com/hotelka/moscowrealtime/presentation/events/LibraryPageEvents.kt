package com.hotelka.moscowrealtime.presentation.events

sealed class LibraryPageEvents {
    data class OnNavigateQuestPage(val questId: String): LibraryPageEvents()
    data class OnSearchQuests(val query: String): LibraryPageEvents()
    object NavigateQuestConstructor: LibraryPageEvents()
    object OnReload: LibraryPageEvents()
}