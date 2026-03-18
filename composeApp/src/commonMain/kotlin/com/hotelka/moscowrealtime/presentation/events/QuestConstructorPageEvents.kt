package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.Location

sealed class QuestConstructorPageEvents {
    object OnSaveQuest : QuestConstructorPageEvents()
    data class OnSearchLocations(val query: String) : QuestConstructorPageEvents()

    data class OnAddTag(val tag: String) : QuestConstructorPageEvents()
    data class OnRemoveTag(val tag: String) : QuestConstructorPageEvents()
    data class OnAddLocation(val location: Location) : QuestConstructorPageEvents()
    data class OnRemoveLocation(val location: Location) : QuestConstructorPageEvents()
    data class OnUpdateQuestTitle(val questTitle: String) : QuestConstructorPageEvents()
    data class OnUpdateQuestDescription(val questDescription: String) : QuestConstructorPageEvents()
    data class OnUpdateQuestLocations(val locations: List<Location>) : QuestConstructorPageEvents()
    object OnSetQuestCover : QuestConstructorPageEvents()
    object OnDetachMapview : QuestConstructorPageEvents()
    data class OnAttachMapView(val mapView: Any) : QuestConstructorPageEvents()
    object OnChangeShowSearchLocations : QuestConstructorPageEvents()
    object OnChangeShowEditInfo : QuestConstructorPageEvents()
    object OnCloseSavingQuestError: QuestConstructorPageEvents()
    object OnChangeShowExitConstructor : QuestConstructorPageEvents()
    object OnGoBack : QuestConstructorPageEvents()
}