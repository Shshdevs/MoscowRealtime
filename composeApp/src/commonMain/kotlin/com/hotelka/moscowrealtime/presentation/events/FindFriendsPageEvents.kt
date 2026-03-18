package com.hotelka.moscowrealtime.presentation.events

sealed class FindFriendsPageEvents {
    data class NavigateUserProfile(val userId: String): FindFriendsPageEvents()
    data class Search(val query: String): FindFriendsPageEvents()
    object RetryLoadUsers: FindFriendsPageEvents()
}