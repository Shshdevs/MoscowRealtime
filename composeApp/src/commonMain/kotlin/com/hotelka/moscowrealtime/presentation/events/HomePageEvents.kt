package com.hotelka.moscowrealtime.presentation.events

sealed class HomePageEvents {
    object OnRetryLoad:HomePageEvents()
    object OnNavigateClosestLocationMap:HomePageEvents()
    data class OnNavigateEvent(val eventId: Int):HomePageEvents()
}