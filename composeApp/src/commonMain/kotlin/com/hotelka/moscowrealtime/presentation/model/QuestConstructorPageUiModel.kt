package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.state.listState.LocationsListState

data class QuestConstructorPageUiModel(
    val tempQuest: TempQuest = TempQuest(),
    val searchLocationsQuery: String = "",
    val searchedLocations: LocationsListState = LocationsListState.Loading,
    val route: Route? = null,
    val isCreatingQuest: Boolean = false,
    val errorCreatingQuest: Boolean = false,
    val showEditInfoAlert: Boolean = false,
    val showEditInfoForm: Boolean = false,
    val showSearchLocationsAlert: Boolean = false,
    val showSearchLocationsForm: Boolean = false,
    val exitConstructorAlertVisible: Boolean = false,
){
    fun saveQuestEnabled(): Boolean{
        val tagsNotEmpty = tempQuest.tags.isNotEmpty()
        val titleLengthOkay = tempQuest.title.isNotEmpty() && tempQuest.title.length <= 300
        val descriptionLengthOkay = tempQuest.description.length >= 20 && tempQuest.description.length <=700
        val locationsSizeOkay = tempQuest.locations.size >= 2
        return tagsNotEmpty && titleLengthOkay && descriptionLengthOkay && locationsSizeOkay
    }
}